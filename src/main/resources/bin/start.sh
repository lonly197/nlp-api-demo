#!/usr/bin/env bash

nohup java -Xms2g -Xmx4g -Dserver.port=7002 -jar *.jar > nohup.log &
echo "Nlp api demo startup ok"