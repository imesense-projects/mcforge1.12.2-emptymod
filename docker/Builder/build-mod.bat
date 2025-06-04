@echo off

:: Build mod
docker build ^
    --file docker\Builder\Dockerfile ^
    --target export ^
    --output type=local,dest=. ^
    .
