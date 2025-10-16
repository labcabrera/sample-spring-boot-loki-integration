#!/bin/bash

set -e

NAMESPACE="sample-cicd"

CA_KEY="ca.key"
CA_CERT="ca.crt"
ARGOCD_KEY="argocd-tls.key"
ARGOCD_CSR="argocd-tls.csr"
ARGOCD_CERT="argocd-tls.crt"

# 1. Generate CA
openssl genrsa -out "$CA_KEY" 2048
openssl req -x509 -new -nodes -key "$CA_KEY" -sha256 -days 3650 -out "$CA_CERT" -subj "/CN=*.local"

# 2. Generate key and CSR for argocd.local
openssl genrsa -out "$ARGOCD_KEY" 2048
openssl req -new -key "$ARGOCD_KEY" -out "$ARGOCD_CSR" -subj "/CN=argocd.local"

# 3. Sign the argocd.local certificate with the CA
openssl x509 -req -in "$ARGOCD_CSR" -CA "$CA_CERT" -CAkey "$CA_KEY" -CAcreateserial -out "$ARGOCD_CERT" -days 365 -sha256

# 4. Create the TLS secret in Kubernetes
kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

kubectl create secret tls argocd-tls --cert="$ARGOCD_CERT" --key="$ARGOCD_KEY" -n "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

echo "argocd.local certificate signed by the created CA. You can use $CA_CERT as the root CA in your clients."
