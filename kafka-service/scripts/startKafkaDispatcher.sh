#!/usr/bin/env bash

app_folder=$1

if [[ -e .pid ]]; then
    echo "A Kafka-Dispatcher instance seems already running from this folder."
    echo "Stop the container first or remove the .pid file if the container is not running."
    exit 1
fi

mkdir -p ${app_folder}/log/
chmod -R a+w+r ${app_folder}/log/
chmod  a+r ${app_folder}/application.yml

name="kd$RANDOM"
echo "$name" > .pid

nohup docker run -p 9098:8080 --rm --name ${name} \
    --userns=host \
    -v ${app_folder}/log/:/log/ \
    -v ${app_folder}/application.yml:/config/application.yml \
    eipm/kafka-dispatcher:1.2.1 &
