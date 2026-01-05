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

# Build project
RUN \
    ./gradlew \
        --no-daemon \
        build

# Final stage
FROM openjdk:8u342-jre-slim-bullseye AS final

# Set metadata
LABEL \
    org.opencontainers.image.vendor="ImeSense" \
    org.opencontainers.image.authors="ImeSense, acidicMercury8, OldSerpskiStalker" \
    org.opencontainers.image.licenses="MIT" \
    org.opencontainers.image.version="0.2" \
    org.opencontainers.image.description="Empty Mod (Minecraft Forge 1.12.2)"

# Set working directory
WORKDIR /app

# Set environment variables
ENV MINECRAFT_VERSION=1.12.2
ENV MCFORGE_VERSION=14.23.5.2864
ENV EULA=false
ENV SERVER_PORT=25565
ENV MAX_MEMORY=2G
ENV MIN_MEMORY=1G

# Install server
RUN \
    apt update && \
    apt install -y wget && \
    wget -O forge-installer.jar \
        https://files.minecraftforge.net/maven/net/minecraftforge/forge/${MINECRAFT_VERSION}-${MCFORGE_VERSION}/forge-${MINECRAFT_VERSION}-${MCFORGE_VERSION}-installer.jar && \
    java -jar \
        forge-installer.jar --installServer && \
    rm forge-installer.jar && \
    mv forge-${MINECRAFT_VERSION}-${MCFORGE_VERSION}.jar forge.jar && \
    apt remove -y wget && \
    apt autoremove -y && \
    rm -rf /var/lib/apt/lists/*

# Copy mod
RUN \
    mkdir /app/mods
COPY --from=build \
    /app/build/libs/*.jar \
    /app/mods

# Copy script
COPY \
    docker/Server/run-server.sh \
    /app
RUN \
    chmod +x run-server.sh

VOLUME [ "/app/world", "/app/config", "/app/crash-reports" ]

# Open port
EXPOSE ${SERVER_PORT}

# Run command
CMD [ "/bin/sh", "-c", "/app/run-server.sh" ]
