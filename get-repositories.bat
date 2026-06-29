@echo off

:: Prepare folders
set dependencies=dep
if not exist "%dependencies%" (
    mkdir %dependencies%
)

:: Get Maven packages store
set packages=maven-packages-store
if not exist "..\%packages%" (
    git clone https://github.com/imesense/%packages%.git "../%packages%"
)
pushd %dependencies%
if not exist "%packages%" (
    mklink /D "%packages%" "..\..\%packages%"
)
popd
