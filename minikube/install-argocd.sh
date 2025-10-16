#!/bin/bash

set -e

NAMESPACE="sample-cicd"

echo "----------------------------------------------------------------------------"
echo " Installing ArgoCD and configuring applications..."
echo "----------------------------------------------------------------------------"

kubectl create namespace "$NAMESPACE" --dry-run=client -o yaml | kubectl apply -f -

helm install argocd argo/argo-cd \
  --namespace "sample-cicd" \
  -f config/argocd-values.yaml
  

find ../argocd -name "*.yaml" -o -name "*.yml" | while read -r file; do
  kubectl apply -f "$file" -n "$NAMESPACE"
done


