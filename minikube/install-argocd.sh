#!/bin/bash

set -e

NAMESPACE="sample-cicd"

echo "----------------------------------------------------------------------------"
echo " Installing ArgoCD and configuring applications..."
echo "----------------------------------------------------------------------------"

helm install argocd argo/argo-cd \
  --namespace "$NAMESPACE" \
  --create-namespace \
  -f ./argocd-values.yaml

find ../argocd -name "*.yaml" -o -name "*.yml" | while read -r file; do
  kubectl apply -f "$file" -n "$NAMESPACE"
done


