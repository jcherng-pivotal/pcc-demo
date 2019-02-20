# pcc-demo



```
Fly PCC : 
fly -t vm set-pipeline --pipeline pcc --config ci/.concourse/pcc/pipeline.yml --load-vars-from ci/.concourse/credentials.yml

Fly PCC-Client : 
fly -t vm set-pipeline --pipeline pcc-client --config ci/.concourse/pcc-demo-client/pipeline.yml --load-vars-from ci/.concourse/credentials.yml
```






change concourse-formula mino file to this: 

```
minio:
  lookup:
    access_key: minio
    secret_key: CHANGEME-05ba7d7c95362608
    endpoint: {{ 'http://' + salt['network.interface_ip']('eth1') + ':9000' }}
    buckets:
      - pcc-demo-model-version
      - pcc-demo-model-snapshot
      - pcc-demo-model-rc
      - pcc-demo-model-release
      - pcc-demo-function-version
      - pcc-demo-function-snapshot
      - pcc-demo-function-rc
      - pcc-demo-function-release
      - pcc-demo-client-version
      - pcc-demo-client-snapshot
      - pcc-demo-client-rc
      - pcc-demo-client-release
      - pcc-artifact-version
      - pcc-artifact-snapshot
      - pcc-artifact-rc
      - pcc-artifact-release
      - docker-registry
```


and run 

```
vagrant ssh -c "sudo salt-call state.apply"
```

in pcc-artifact-release bucket on minio add:
```
mapstruct-jdk8-1.2.0.Final.jar
```