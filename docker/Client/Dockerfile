#syntax=docker/dockerfile:1

# Build stage
FROM openjdk:8u342-jdk-slim-bullseye AS build

# Set working directory
WORKDIR /app

# Copy project
COPY . .

# Set cache
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
