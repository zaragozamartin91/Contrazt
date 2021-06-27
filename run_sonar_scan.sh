#!/usr/bin/env bash

mvn sonar:sonar \
  -Dsonar.projectKey=contrazt \
  -Dsonar.host.url=http://192.168.99.100:9000 \
  -Dsonar.login=36060419677b4c37197d1d6688d79e7a90495757
