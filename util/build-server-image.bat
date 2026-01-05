@echo off

:: Set parameters
if "%~1"=="" (
    echo Version not specified!
    echo Usage example:
    echo     %~0 vX
    exit /b 1
)
set ProjectVersion=%~1

:: Set variables
set ProjectName=mcforge1.12.2-empty-mod
set ProjectTag=%ProjectName%-server
set ProjectOutputImage=%ProjectName%-%ProjectVersion%-server-image.tar

:: Create folders
if not exist out\%ProjectVersion% (
    mkdir out\%ProjectVersion%
)

:: Get timestamp
for /f "delims=" %%i in (
    'git rev-parse HEAD'
) do set "CommitHash=%%i"
for /f "delims=" %%i in (
    'call util\get-timestamp.bat %CommitHash%'
) do set "CommitTimestamp=%%i"
set ProjectTimestamp=t%CommitTimestamp%
echo %ProjectTimestamp%

:: Build image
docker ^
    build ^
    --file docker\Server\Dockerfile ^
    --progress=plain ^
    --tag %ProjectTag%:latest ^
    --tag %ProjectTag%:%ProjectTimestamp% ^
    --tag %ProjectTag%:%ProjectVersion% ^
    --tag ghcr.io/imesense/%ProjectTag%:latest ^
    --tag ghcr.io/imesense/%ProjectTag%:%ProjectTimestamp% ^
    --tag ghcr.io/imesense/%ProjectTag%:%ProjectVersion% ^
    .

:: Export image
docker ^
    save ^
    --output %ProjectOutputImage% ^
    %ProjectTag%:latest ^
    %ProjectTag%:%ProjectTimestamp% ^
    %ProjectTag%:%ProjectVersion% ^
    ghcr.io/imesense/%ProjectTag%:latest ^
    ghcr.io/imesense/%ProjectTag%:%ProjectTimestamp% ^
    ghcr.io/imesense/%ProjectTag%:%ProjectVersion%
move ^
    %ProjectOutputImage% ^
    out\%ProjectVersion%\%ProjectOutputImage%
