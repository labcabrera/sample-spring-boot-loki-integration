#!/bin/bash

set -e

echo "----------------------------------------------------------------------------"
echo " Installing ArgoCD and configuring applications..."
echo "----------------------------------------------------------------------------"

helm install argocd argo/argo-cd \
  --namespace "$NAMESPACE_TOOLS" \
  --create-namespace \
  --set server.service.type=NodePort \
  --set configs.secret.argocdServerAdminPassword='$2y$10$vAKJfNlItZH/h500v0DObOd5IFBAUJifgSLTiVpKzqJ2AKGhqozVy'

find ../argocd -name "*.yaml" -o -name "*.yml" | while read -r file; do
  kubectl apply -f "$file" -n "$NAMESPACE_TOOLS"
done
