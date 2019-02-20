#!/usr/bin/env bash
function buildGradle() {
    ./gradlew clean build -Pversion=${2}
    cp */build/libs/*.jar ../${1}/
}

function buildMaven() {
    ./mvnw clean package -Drevision=${2}
    cp */target/*.jar ../${1}/
}


function build() {
    local projectType="${1}"
    local artifacts="${2}"
    local version="${3}"

    if [[ ${projectType} == "MAVEN" ]]; then
        buildMaven ${artifacts} ${version}
    else
        buildGradle ${artifacts} ${version}
    fi
}