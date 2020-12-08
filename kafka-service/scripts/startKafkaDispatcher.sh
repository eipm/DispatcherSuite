#!/usr/bin/env bash

app_folder=$1

# check if the app folder exists
if [[ -z "$app_folder" ]]; then
      echo "ERROR: config folder not set"
      echo "Usage: startKafkaDispatcher.sh <config folder>"
      exit 1
else
      echo "Using ${app_folder} as config folder"
fi

# check if the config file is available
if [[ ! -e ${app_folder}/application.yml ]]; then
    echo "ERROR: file ${app_folder}/application.yml not found."
    exit 1
fi

# check if another instance is running
if [[ -e .pid ]]; then
    echo "A Kafka-Dispatcher instance seems already running from this folder."
    echo "Stop the container first or remove the .pid file if the container is not running."
    exit 1
fi

name="kd$RANDOM"
echo "$name" > .pid

nohup docker run -p 9098:8080 --rm --name ${name} \
    --userns=host \
    -v ${app_folder}/application.yml:/config/application.yml \
    cgen/kafka-dispatcher:1.2.1 &