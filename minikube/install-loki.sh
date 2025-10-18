#!/bin/bash

set -e

helm install loki grafana/loki-stack \
  --namespace "$NAMESPACE" \
  --set promtail.enabled=true \
  --set loki.persistence.enabled=true \
  --set loki.persistence.size=10Gi \
  --set loki.service.type=NodePort \
  --timeout=600s
