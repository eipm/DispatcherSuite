#!/usr/bin/env bash

app_folder=$1

if [[ -z "$app_folder" ]]; then
      echo "ERROR: config folder not set"
      echo "Usage: startKafkaDispatcherSSH.sh <config folder>"
      exit 1
else
      echo "Using ${app_folder} as config folder"
fi

if [[ -e .pid ]]; then
    echo "A Kafka-Dispatcher instance seems already running from this folder."
    echo "Stop the container first or remove the .pid file if the container is not running."
    exit 1
fi
mkdir -p ${app_folder}/log/
name="kd$RANDOM"
echo "$name" > .pid

nohup docker run -p 9098:8080 --rm --name ${name} \
    -e HOST_HOSTNAME=$(hostname) \
    -e HOST_USER=$LOGNAME \
    -e HOST_USER_ID=$(id -u) \
    -v ${app_folder}/log/:/log/ \
    -v $HOME/.ssh/:/ssh/:ro \
    --userns=host \
    -v ${app_folder}/application.yml:/config/application.yml \
    eipm/kafka-dispatcher:1.2.1 &