# pcc-demo


Fly PCC : fly -t vm set-pipeline --pipeline pcc --config ci/.concourse/pcc/pipeline.yml --load-vars-from ci/.concourse/credentials.yml
Fly PCC-Client : fly -t vm set-pipeline --pipeline pcc-client --config ci/.concourse/pcc-demo-client/pipeline.yml --load-vars-from ci/.concourse/credentials.yml
