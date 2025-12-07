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
set ProjectName=mcforge1.12.2-emptymod
set ProjectOutputImage=%ProjectName%-%ProjectVersion%-server-image.tar

:: Get timestamp
for /f %%a in (
    'powershell -Command "[math]::Round((New-TimeSpan -Start (Get-Date \"1970-01-01\") -End (Get-Date)).TotalSeconds)"'
) do set ProjectTimestamp=t%%a

:: Build image
docker ^
    build ^
    --file docker\Server\Dockerfile ^
    --progress=plain ^
    --tag %ProjectName%:latest ^
    --tag %ProjectName%:%ProjectTimestamp% ^
    --tag %ProjectName%:%ProjectVersion% ^
    --tag ghcr.io/imesense/%ProjectName%:latest ^
    --tag ghcr.io/imesense/%ProjectName%:%ProjectTimestamp% ^
    --tag ghcr.io/imesense/%ProjectName%:%ProjectVersion% ^
    .

:: Export image
docker ^
    save ^
    --output %ProjectOutputImage% ^
    %ProjectName%:latest ^
    %ProjectName%:%ProjectTimestamp% ^
    %ProjectName%:%ProjectVersion% ^
    ghcr.io/imesense/%ProjectName%:latest ^
    ghcr.io/imesense/%ProjectName%:%ProjectTimestamp% ^
    ghcr.io/imesense/%ProjectName%:%ProjectVersion%
move ^
    %ProjectOutputImage% ^
    out\%ProjectVersion%\%ProjectOutputImage%
