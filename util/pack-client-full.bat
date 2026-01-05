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
set ProjectOutputPublish=%ProjectName%-%ProjectVersion%-client-publish.zip
set ProjectOutputRepository=%ProjectName%-%ProjectVersion%-client-repository.zip

:: Create folders
if not exist out\%ProjectVersion% (
    mkdir out\%ProjectVersion%
)

:: Pack publish
pushd ^
    build\libs
%SevenZip% ^
    a ^
    -tzip ^
        "%ProjectOutputPublish%"
popd
move ^
    build\libs\%ProjectOutputPublish% ^
    out\%ProjectVersion%\%ProjectOutputPublish%

:: Pack repository
%SevenZip% ^
    a ^
    -tzip ^
        "%ProjectOutputRepository%" ^
    -ir!"mcmodsrepo"
move ^
    %ProjectOutputRepository% ^
    out\%ProjectVersion%\%ProjectOutputRepository%
