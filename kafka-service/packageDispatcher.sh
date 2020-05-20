#!/usr/bin/env bash
cd ../executors && mvn clean install
cd -
mvn -U -Dmaven.test.skip=true clean package
