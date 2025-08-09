#!/usr/bin/env bash

# Build client
docker build \
    --file docker/Client/Dockerfile \
    --progress=plain \
    --target export \
    --output type=local,dest=. \
    .
