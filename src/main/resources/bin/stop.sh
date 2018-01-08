#!/usr/bin/env bash

ps_pid=`ps -ef|grep *.jar|grep -v grep|awk '{print $2}'`
kill -9 ${ps_pid}
echo "Nlp api demo stop ok"