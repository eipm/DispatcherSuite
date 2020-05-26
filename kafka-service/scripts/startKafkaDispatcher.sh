#!/usr/bin/env bash

app_folder=$1

if [[ -e .pid ]]; then
    echo "A Kafka-Dispatcher instance seems already running from this folder."
    echo "Stop the container first or remove the .pid file if the container is not running."
    exit 1
fi

name="kd$RANDOM"
echo "$name" > .pid

nohup docker run -p 9098:8080 --rm --name ${name} \
    -e HOST_HOSTNAME=$(hostname) \
    -e HOST_USER=$LOGNAME \
    -e HOST_USER_ID=$(id -u) \
    -v $HOME/.ssh/:/ssh/:ro \
    --userns=host \
    -v ${app_folder}/application.yml:/config/application.yml \
    eipm/kafka-dispatcher &