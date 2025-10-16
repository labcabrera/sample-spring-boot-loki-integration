#!/bin/bash

set -e

NAMESPACE="sample-cicd"

echo "----------------------------------------------------------------------------"
echo " Installing ArgoCD and configuring applications..."
echo "----------------------------------------------------------------------------"

helm install argocd argo/argo-cd \
  --namespace "sample-cicd" \
  --create-namespace \
  --set server.service.type=ClusterIP \
  --set configs.secret.argocdServerAdminPassword='$2y$10$vAKJfNlItZH/h500v0DObOd5IFBAUJifgSLTiVpKzqJ2AKGhqozVy'

kubectl apply -f certificates/argocd-certificate.yaml -n "sample-cicd"

kubectl apply -f ingress/argocd-ingress.yaml -n "sample-cicd"

find ../argocd -name "*.yaml" -o -name "*.yml" | while read -r file; do
  kubectl apply -f "$file" -n "$NAMESPACE"
done


