@echo off

:: Build server
docker build ^
    --file docker\Server\Dockerfile ^
    --progress=plain ^
    --target final ^
    --tag mcforge1.12.2-modification-boilerplate-server:latest ^
    --tag ghcr.io/imesense/mcforge1.12.2-modification-boilerplate-server:latest ^
    .
