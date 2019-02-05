#!/usr/bin/env bash
set -eux

VERSION=$(cat version/number)
FILE_NAME=$(cd artifact && ls *-*-*.*)
ARTIFACT_NAME=${FILE_NAME%-*-*.*}
EXTENSION=${FILE_NAME##*.}

cp artifact/${FILE_NAME} post-artifact/${ARTIFACT_NAME}-${VERSION}.${EXTENSION}