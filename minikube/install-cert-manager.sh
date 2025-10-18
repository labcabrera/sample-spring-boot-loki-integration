#!/bin/bash

set -e

CERT_MANAGER_VERSION="v1.14.4"
NAMESPACE_CERT_MANAGER="cert-manager"
ROOT_CA_KEY="./certs/rootCA.key"
ROOT_CA_CERT="./certs/rootCA.crt"
ISSUER_CERT="./certs/clusterissuer-sample-root-ca.yaml"

kubectl create namespace "$NAMESPACE_CERT_MANAGER" --dry-run=client -o yaml | kubectl apply -f -

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/latest/download/cert-manager.crds.yaml

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager \
  --namespace "$NAMESPACE_CERT_MANAGER" \
  --version "$CERT_MANAGER_VERSION"

kubectl create secret tls sample-root-ca \
  --cert="$ROOT_CA_CERT" \
  --key="$ROOT_CA_KEY" \
  -n "$NAMESPACE_CERT_MANAGER"

kubectl apply -f "$ISSUER_CERT"