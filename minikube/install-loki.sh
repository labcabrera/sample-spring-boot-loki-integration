#!/bin/bash

set -e

helm install loki grafana/loki-stack \
  --namespace monitoring \
  --set grafana.enabled=true,promtail.enabled=true
