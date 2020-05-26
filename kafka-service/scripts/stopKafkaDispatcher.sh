#!/usr/bin/env bash

name=`cat .pid`
docker stop ${name} &&  rm .pid