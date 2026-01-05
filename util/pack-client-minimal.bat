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
set ProjectOutputBuild=%ProjectName%-%ProjectVersion%-client.zip

:: Create folders
if not exist out\%ProjectVersion% (
    mkdir out\%ProjectVersion%
)

:: Pack build
pushd ^
    build\libs
%SevenZip% ^
    a ^
    -tzip ^
        "%ProjectOutputBuild%"
popd
move ^
    build\libs\%ProjectOutputBuild% ^
    out\%ProjectVersion%\%ProjectOutputBuild%
