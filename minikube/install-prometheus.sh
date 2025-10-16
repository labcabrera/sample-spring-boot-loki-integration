#!/bin/bash

set -e

NAMESPACE="sample-observability"

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

echo "----------------------------------------------------------------------------"
echo "Creating namespace..."
echo "----------------------------------------------------------------------------"

kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

echo "----------------------------------------------------------------------------"
echo "Installing Prometheus..."
echo "----------------------------------------------------------------------------"

helm install prometheus prometheus-community/kube-prometheus-stack \
  --namespace "$NAMESPACE" \
  --set prometheus.prometheusSpec.serviceMonitorSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.podMonitorSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.ruleSelectorNilUsesHelmValues=false \
  --set prometheus.prometheusSpec.retention=30d \
  --set prometheus.prometheusSpec.storageSpec.volumeClaimTemplate.spec.resources.requests.storage=10Gi \
  --set prometheus.prometheusSpec.serviceMonitorNamespaceSelector.any=true \
  --set prometheus.prometheusSpec.serviceMonitorSelector.matchLabels.app=mcm-demo \
  --set alertmanager.enabled=true \
  --set grafana.enabled=true \
  --set grafana.adminPassword=changeit \
  --set grafana.persistence.enabled=true \
  --set grafana.persistence.size=5Gi \
  --set grafana.service.type=NodePort \
  --set prometheus.service.type=NodePort \
  --timeout=600s

echo "----------------------------------------------------------------------------"
echo "Installing Loki and Promtail..."
echo "----------------------------------------------------------------------------"

helm install loki grafana/loki-stack \
  --namespace "$NAMESPACE" \
  --set promtail.enabled=true \
  --set loki.persistence.enabled=true \
  --set loki.persistence.size=10Gi \
  --set loki.service.type=NodePort \
  --timeout=600s

echo "----------------------------------------------------------------------------"
echo "Configuring Ingress..."
echo "----------------------------------------------------------------------------"

kubectl apply -f ingress/grafana-ingress.yaml -n "$NAMESPACE"
kubectl apply -f ingress/prometheus-ingress.yaml -n "$NAMESPACE"
