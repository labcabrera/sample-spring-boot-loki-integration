#!/bin/bash

set -e

NAMESPACE="sample-cert-manager"
NAMESPACE_ARGOCD="sample-cicd"

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/latest/download/cert-manager.crds.yaml

kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager --namespace "$NAMESPACE" --version v1.14.4

kubectl apply -f ./cert-manager-issuer.yaml -n "$NAMESPACE_ARGOCD"

kubectl apply -f ./cert-manager-certificate.yaml -n "$NAMESPACE_ARGOCD"