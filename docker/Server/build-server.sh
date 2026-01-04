#!/usr/bin/env bash

# Build server
docker build \
    --file docker/Server/Dockerfile \
    --progress=plain \
    --target final \
    --tag mcforge1.12.2-empty-mod-server:latest \
    --tag ghcr.io/imesense/mcforge1.12.2-empty-mod-server:latest \
    .
