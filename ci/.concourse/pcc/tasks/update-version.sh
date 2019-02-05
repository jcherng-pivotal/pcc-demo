#!/usr/bin/env bash
set -eux

VERSION=$(cat version/number)

cp pcc-demo-model/*.jar post-artifacts/pcc-demo-model-${VERSION}.jar
cp pcc-demo-function/*.jar post-artifacts/pcc-demo-function-${VERSION}.jar