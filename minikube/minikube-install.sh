#!/bin/bash

set -e

MINIKUBE_PROFILE="sample-loki"
MINIKUBE_MEMORY=8192
MINIKUBE_CPUS=4
MINIKUBE_DISK=20g
K8S_VERSION=v1.33.1
NAMESPACE_APPS="apps-local"

echo "----------------------------------------------------------------------------"
echo "Starting minikube..."
echo "----------------------------------------------------------------------------"

minikube start \
  --profile "$MINIKUBE_PROFILE" \
  --driver=docker \
  --memory=$MINIKUBE_MEMORY \
  --cpus=$MINIKUBE_CPUS \
  --disk-size=$MINIKUBE_DISK \
  --kubernetes-version=$K8S_VERSION

minikube addons enable metrics-server --profile "$MINIKUBE_PROFILE"

minikube addons enable ingress --profile "$MINIKUBE_PROFILE"

kubectl create namespace "$NAMESPACE_APPS"
