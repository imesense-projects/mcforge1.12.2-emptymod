#!/usr/bin/env bash

# Build server
docker build \
    --file docker/Server/Dockerfile \
    --progress=plain \
    --target final \
    --tag mcforge1.12.2-emptymod:latest \
    --tag ghcr.io/imesense-projects/mcforge1.12.2-emptymod:latest \
    .
