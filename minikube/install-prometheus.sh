#!/bin/bash

set -e

NAMESPACE="sample-observability"

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

helm install prometheus prometheus-community/kube-prometheus-stack \
  --namespace "$NAMESPACE" \
  --set prometheus.prometheusSpec.serviceMonitorSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.podMonitorSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.ruleSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.retention=30d \
  --set prometheus.prometheusSpec.storageSpec.volumeClaimTemplate.spec.resources.requests.storage=10Gi \
  --set prometheus.prometheusSpec.serviceMonitorNamespaceSelector.any=true \
  --set prometheus.prometheusSpec.serviceMonitorSelector.matchLabels.app=sample-loki-api \
  --set alertmanager.enabled=true \
  --set grafana.enabled=true \
  --set grafana.adminPassword=changeit \
  --set grafana.persistence.enabled=true \
  --set grafana.persistence.size=5Gi \
  --set grafana.service.type=NodePort \
  --set prometheus.service.type=NodePort \
  --timeout=600s

kubectl apply -f ./certs/grafana-cert.yaml -n "$NAMESPACE"

kubectl apply -f ./ingress/grafana-ingress.yaml -n "$NAMESPACE"
kubectl apply -f ./ingress/prometheus-ingress.yaml -n "$NAMESPACE"
