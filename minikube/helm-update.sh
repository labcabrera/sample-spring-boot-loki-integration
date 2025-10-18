#!/bin/bash

set -e

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts

echo "Updating Helm repositories..."

helm repo update

echo "Repositories updated."
