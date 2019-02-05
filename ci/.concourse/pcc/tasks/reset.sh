#!/usr/bin/env bash
set -eux

export TERM=dumb
export JARS_DIR=$( pwd )/artifacts

set +x
/gemfire/bin/gfsh \
 -e "set variable --name=JARS_DIR --value=${JARS_DIR}" \
 -e "connect --use-http=true --url=${PCC_URL} --user=${PCC_USER} --password=${PCC_PASSWORD}" \
 -e "run --file=pcc-script-repo/db/pcc/gfsh/destroy-regions.gfsh" \
 -e "run --file=pcc-script-repo/db/pcc/gfsh/create-regions.gfsh" \
 -e "run --file=pcc-script-repo/db/pcc/gfsh/create-indexes.gfsh" \
 -e "run --file=pcc-script-repo/db/pcc/gfsh/deploy-jars.gfsh" \
 -e "run --file=pcc-script-repo/db/pcc/gfsh/get-info.gfsh"