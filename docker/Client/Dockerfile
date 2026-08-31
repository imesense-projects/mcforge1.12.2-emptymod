#syntax=docker/dockerfile:1

# Build stage
FROM openjdk:8u342-jdk-slim-bullseye AS build

# Install CA certificates
RUN \
    apt update && \
    apt install -y \
        ca-certificates-java && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy project
COPY . .

# Get Maven repository
RUN \
    apt update && \
    apt install -y \
        git \
        git-lfs && \
    git clone \
        --depth=1 \
        --branch d2026.7.5 \
        https://github.com/imesense/maven-packages-store \
        /app/dep/maven-packages-store && \
    apt remove -y \
        git \
        git-lfs && \
    apt autoremove -y && \
    rm -rf /var/lib/apt/lists/*

# Set Gradle cache
ENV GRADLE_USER_HOME=/gradle
RUN \
    mkdir -p ${GRADLE_USER_HOME} && \
    mkdir -p ${GRADLE_USER_HOME}/{wrapper,caches}

# Build modification
RUN \
    ./gradlew \
        --no-daemon \
        build

# Export stage
FROM scratch AS export

# Copy output
COPY --from=build \
    /app/build/libs/*.jar \
    ./build/libs/
