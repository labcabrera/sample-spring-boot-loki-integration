#!/bin/bash

set -e

NAMESPACE="sample-cicd"

echo "----------------------------------------------------------------------------"
echo " Installing ArgoCD and configuring applications..."
echo "----------------------------------------------------------------------------"

# helm install argocd argo/argo-cd \
#   --namespace "sample-cicd" \
#   --create-namespace \
#   --set configs.secret.argocdServerAdminPassword='$2y$10$vAKJfNlItZH/h500v0DObOd5IFBAUJifgSLTiVpKzqJ2AKGhqozVy' \
#   --set server.service.type=ClusterIP \
#   --set server.service.type=ClusterIP \
#   --set server.service.ports.https=443 \
#   --set server.service.ports.http=80

helm install argocd argo/argo-cd \
  --namespace "sample-cicd" \
  -f argocd-values.yaml
  
# kubectl apply -f certs/argocd-cert.yaml -n "sample-cicd"

# kubectl apply -f ingress/argocd-ingress.yaml -n "sample-cicd"

find ../argocd -name "*.yaml" -o -name "*.yml" | while read -r file; do
  kubectl apply -f "$file" -n "$NAMESPACE"
done


