@echo off

:: Set parameters
if "%~1"=="" (
    echo Version not specified!
    echo Usage example:
    echo     %~0 vX
    exit /b 1
)
set ProjectVersion=%~1

:: Set environment
set SevenZip="%ProgramFiles%"\7-Zip\7z.exe

:: Set variables
set ProjectName=mcforge1.12.2-empty-mod
set ProjectOutputConfigs=%ProjectName%-%ProjectVersion%-server-configs.zip

:: Create folders
if not exist tmp (
    mkdir tmp
)
if not exist out\%ProjectVersion% (
    mkdir out\%ProjectVersion%
)

:: Copy configs
copy ^
    docker\Server\docker-compose.yaml ^
    tmp\docker-compose.yaml
copy ^
    docker\Server\.env.example ^
    tmp\.env
copy ^
    docker\Server\server.properties.example ^
    tmp\server.properties

:: Pack configs
pushd ^
    tmp
%SevenZip% ^
    a ^
    -tzip ^
        "%ProjectOutputConfigs%"
popd
move ^
    tmp\%ProjectOutputConfigs% ^
    out\%ProjectVersion%\%ProjectOutputConfigs%
rmdir ^
    /s /q ^
    tmp
