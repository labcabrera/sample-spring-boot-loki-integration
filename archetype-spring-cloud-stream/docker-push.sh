#!/bin/bash

./gradlew clean build -x test

docker build -t labcabrera/sample-loki-api:latest .

docker tag labcabrera/sample-loki-api:latest labcabrera/sample-loki-api:latest

docker push labcabrera/sample-loki-api:latest
