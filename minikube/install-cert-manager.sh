#!/bin/bash

set -e

kubectl create namespace cert-manager --dry-run=client -o yaml | kubectl apply -f -

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/latest/download/cert-manager.crds.yaml

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager \
  --namespace cert-manager \
  --version v1.14.4

kubectl create secret tls sample-root-ca \
  --cert=rootCA.crt \
  --key=rootCA.key \
  -n cert-manager

kubectl apply -f clusterissuer-local-root-ca.yaml