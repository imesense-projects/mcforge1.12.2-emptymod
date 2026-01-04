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
call build-client-minimal.bat
call pack-client-minimal.bat %ProjectVersion%
call build-client-full.bat
call pack-client-full.bat %ProjectVersion%

:: Build and pack server
call build-server-image.bat %ProjectVersion%
call pack-server-configs.bat %ProjectVersion%

:: Pack cache
call pack-gradle-cache.bat %ProjectVersion%

:: Build and pack DevContainer
call build-devcontainer-image.bat %ProjectVersion%
