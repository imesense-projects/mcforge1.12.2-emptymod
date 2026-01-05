@echo off

:: Set version
if "%~1"=="" (
    echo Version not specified!
    echo Usage example:
    echo     %~0 vX
    exit /b 1
)
set ProjectVersion=%~1

:: Build and pack client
call util\build-client-minimal.bat
call util\pack-client-minimal.bat %ProjectVersion%
call util\build-client-full.bat
call util\pack-client-full.bat %ProjectVersion%

:: Build and pack server
call util\build-server-image.bat %ProjectVersion%
call util\pack-server-configs.bat %ProjectVersion%

:: Pack cache
call util\pack-gradle-cache.bat %ProjectVersion%

:: Build and pack DevContainer
call util\build-devcontainer-image.bat %ProjectVersion%
