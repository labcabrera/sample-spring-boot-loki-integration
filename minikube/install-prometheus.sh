#!/bin/bash

set -e

NAMESPACE="sample-observability"

kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

helm install monitoring prometheus-community/kube-prometheus-stack \
  --namespace "$NAMESPACE" \
  --set grafana.enabled=true \
  --set grafana.adminPassword=changeit \
  --set grafana.service.type=ClusterIP \
  --set grafana.ingress.enabled=false \
  --set grafana.persistence.enabled=false \
  --set prometheus.ingress.enabled=false \
  --set alertmanager.ingress.enabled=false \
  --set prometheus.prometheusSpec.serviceMonitorSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.podMonitorSelectorNilUsesHelmValues=false

kubectl apply -f ./certs/grafana-cert.yaml -n "$NAMESPACE"

kubectl apply -f ./ingress/grafana-ingress.yaml -n "$NAMESPACE"
kubectl apply -f ./ingress/prometheus-ingress.yaml -n "$NAMESPACE"
