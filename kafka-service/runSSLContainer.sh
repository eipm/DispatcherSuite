#!/usr/bin/env bash

WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

docker run -p 8443:8443 --rm  \
 -e HOST_HOSTNAME=$(hostname) \
 -e HOST_USER=$LOGNAME \
 -v "${WORKING_DIR}/application-ssl.yml":/application.yml \
  -v /Users/manuelesimi/EIPM/DispatcherSuite/kafka-service/scripts/kd-keystore.jks:/kd-keystore.jks \
  cgen/kafka-dispatcher:1.3.0.patched20210201

