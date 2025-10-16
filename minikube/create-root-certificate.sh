#!/bin/bash

set -e

CA_KEY="rootCA.key"
CA_CERT="rootCA.crt"

openssl genrsa -out "$CA_KEY" 2048

openssl req -x509 -new -nodes \
  -sha256 \
  -days 3650 \
  -key "$CA_KEY" \
  -out "$CA_CERT" \
  -subj "/CN=*.local"
