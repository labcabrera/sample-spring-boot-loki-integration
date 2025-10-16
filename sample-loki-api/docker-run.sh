#!/bin/bash

./gradlew clean build -x test

docker stop sample-loki-api

docker rm sample-loki-api

docker rmi labcabrera/sample-loki-api:latest

docker build -t labcabrera/sample-loki-api:latest .

docker run -d --name sample-loki-api -p 8081:8081 \
  -e JAVA_OPTS="-Xms512m -Xmx1024m" \
  -e LOG_LEVEL="DEBUG" \
  labcabrera/sample-loki-api:latest

docker logs -f sample-loki-api