#!/usr/bin/env bash
cd ../executors && mvn clean install
cd -
mvn -U spring-boot:run