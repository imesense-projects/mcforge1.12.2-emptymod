@echo off

:: Build mod
docker build ^
    --file docker\Builder\Dockerfile ^
    --progress=plain ^
    --target export ^
    --output type=local,dest=. ^
    --cache-to type=local,dest=.\.docker\cache ^
    --cache-from type=local,src=.\.docker\cache ^
    .
