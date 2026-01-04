@echo off

setlocal enabledelayedexpansion

:: Set parameters
if "%~1"=="" (
    echo Usage example:
    echo     %~0 adcbf21f1827
    exit /b 1
)
set "CommitHash=%~1"

:: Get date from Git
for /f "delims=" %%d in (
    'git show -s --pretty^=format:^"%%ad^" --date^=format:^"%%Y-%%m-%%d %%H:%%M:%%S^" "%CommitHash%" 2^>nul'
) do (
    set "CommitDate=%%d"
)
if not defined CommitDate (
    echo Error: Commit with "%CommitHash%" hash not found
    exit /b 1
)

:: Convert date to timestamp
for /f %%t in (
    'powershell -Command "[datetime]::ParseExact('%CommitDate%', 'yyyy-MM-dd HH:mm:ss', [System.Globalization.CultureInfo]::InvariantCulture).ToUniversalTime().Subtract([datetime]'1970-01-01').TotalSeconds"'
) do (
    set "CommitTimestamp=%%t"
)
echo %CommitTimestamp%

endlocal
