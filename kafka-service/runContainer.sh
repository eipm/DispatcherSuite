#!/usr/bin/env bash
set -x
WORKING_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
 docker run -p 8080:8080 --rm \
 -v /Users/manuelesimi/tmp/:/tmp \
 -v /Users/manuelesimi/EIPM/DispatcherSuite/kafka-service/target/:/log \
 -v "${WORKING_DIR}/application.yml":/application.yml \
  eipm/kafka-dispatcher:1.2.1
