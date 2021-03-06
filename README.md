
## Local CI/CD Configurations for Pivotal Cloud Cache (PCC)
A sample project has been provided that showcases common PCC application design patterns.

### Clone concourse-ci-formula for Concourse, Minio, and Vault (**All-In-One** VM)
The concourse CI/CD pipelines configured for this demo project requires docker container and S3 buckets. For local testing, [concourse-ci-formula](https://github.com/marco-m/concourse-ci-formula) provides an easy to use all-in-one local setup by using `Vagrant` and `VirtualBox`.

### Update concourse-ci-formula
Additional s3 buckets will need to be added to the local Minio for the pipelines to work.

Please modify the `concourse-ci-formula/saltstack/pillar/minio.sls` based on the following example.
```
minio:
  lookup:
    access_key: minio
    secret_key: CHANGEME-05ba7d7c95362608
    endpoint: {{ 'http://' + salt['network.interface_ip']('eth1') + ':9000' }}
    buckets:
      - pcc-demo-client-version
      - pcc-demo-client-snapshot
      - pcc-demo-client-rc
      - pcc-demo-client-release
      - pcc-demo-loader-version
      - pcc-demo-loader-snapshot
      - pcc-demo-loader-rc
      - pcc-demo-loader-release
      - pcc-artifact-version
      - pcc-artifact-snapshot
      - pcc-artifact-rc
      - pcc-artifact-release
      - docker-registry
```

### Start Local Concourse, Minio, and Vault
By following the instructions from [concourse-ci-formula](https://github.com/marco-m/concourse-ci-formula) for steps on how to setup and start the local all-in-one vm.

### Create _credentials.yml_
__NOTE:__ The following example of `credentials.yml` is configured to use git daemon on local machine and concourse setup from concourse-formula. Either `GRADLE` or `MAVEN` can be used as project type.
```
git-uri: git://10.0.2.2/pcc-demo
git-branch: master
cf-api:
cf-org:
cf-space-dev:
cf-space-prod:
cf-username:
cf-password:
pcc-gfsh-url-dev:
pcc-gfsh-user-dev:
pcc-gfsh-password-dev:
pcc-gfsh-url-prod:
pcc-gfsh-user-prod:
pcc-gfsh-password-prod:
pivnet-api-token:
s3-endpoint: http://192.168.50.4:9000
s3-access-key-id: minio
s3-secret-access-key: CHANGEME-05ba7d7c95362608
project-type: MAVEN
```

### Upload Third Party Libraries to Minio
Please upload the following jars to the `pcc-artifact-release` bucket on Minio. This project has dependency on third party libraries that needs to be deployed to PCC by the CI/CD pipeline.

__NOTE__ Deploying PCC server functions and dependency jars is meant for designs that have high performance requirement. For simple caching use cases, server function is optional. The demo application just showcases the example of how components can be deployed to PCC servers with automation.

[mapstruct-1.3.0.Final.jar](http://central.maven.org/maven2/org/mapstruct/mapstruct-processor/1.3.0.Final/mapstruct-processor-1.3.0.Final.jar)


### Start Git Daemon Locally
```
git daemon --verbose --export-all --enable=upload-pack --enable=receive-pack --reuseaddr --base-path=$PARENT_DIRECTORY_OF_GIT_REPOSITORIES
```
### Fly _login_
The following command is required to log into concourse in order to setup the pipelines 
```
fly -t vm login -c http://192.168.50.4:8080 -u concourse -p CHANGEME-8bef502c6d4da90b
```

### Fly _pcc-demo-client_ Concourse Pipeline
The following command is used to setup the pipeline for `pcc-demo-client` application. The pipeline will test-build and deploy (blue-green) the application the Cloud Foundry.
```
fly -t vm set-pipeline --pipeline pcc-demo-client --config ci/.concourse/pcc-demo-client/pipeline.yml --load-vars-from ci/.concourse/credentials.yml
```

### Fly _pcc-demo-loader_ Concourse Pipeline
The following command is used to setup the pipeline for `pcc-demo-loader` application. The pipeline will test-build and deploy (blue-green) the application the Cloud Foundry.
```
fly -t vm set-pipeline --pipeline pcc-demo-loader --config ci/.concourse/pcc-demo-loader/pipeline.yml --load-vars-from ci/.concourse/credentials.yml
```

### Fly _pcc_ Concourse Pipeline
```
fly -t vm set-pipeline --pipeline pcc --config ci/.concourse/pcc/pipeline.yml --load-vars-from ci/.concourse/credentials.yml
```
