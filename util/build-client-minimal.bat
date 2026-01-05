@echo off

:: Set environment
set JAVA_VARIANT=temurin
set JAVA_VERSION=1.8.0_422
set JAVA_HOME=%USERPROFILE%\.jdks\%JAVA_VARIANT%-%JAVA_VERSION%
set GRADLE_USER_HOME=.gradle

:: Build client
call gradlew.bat ^
    --no-daemon ^
    build
