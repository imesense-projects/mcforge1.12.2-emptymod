#!/usr/bin/env bash

# Build project
docker build \
    --file docker/Builder/Dockerfile \
    --target cache \
    --output type=local,dest=. \
    .
