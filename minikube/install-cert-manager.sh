#!/bin/bash

set -e

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/latest/download/cert-manager.crds.yaml

kubectl create namespace cert-manager

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager --namespace cert-manager --version v1.14.4

kubectl apply -f ./argocd-issuer.yaml -n sample-cicd

kubectl apply -f ./argocd-certificate.yaml -n sample-cicd