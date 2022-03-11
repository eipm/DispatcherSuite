#!/usr/bin/env bash
set -x
WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
 docker run --rm -p 8080:8080\
 --userns=host \
 -e DISPATCHER_PORT=8080 \
 -e HOST_HOSTNAME=$(hostname).med.cornell.edu \
 -e HOST_USER=$LOGNAME \
 -e HOST_USER_ID=$LOGNAME \
 -v /Users/manuelesimi/tmp/:/tmp \
 -v /Users/manuelesimi/EIPM/DispatcherSuite/kafka-service/log/:/log \
 -v "${WORKING_DIR}/application.yml":/application.yml \
  cgen/kafka-dispatcher:1.3.2
