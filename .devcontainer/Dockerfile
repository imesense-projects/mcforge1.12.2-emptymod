#syntax=docker/dockerfile:1

# Base image
ARG DEBIAN_VARIANT="bullseye"
ARG JAVA_VERSION="1-8"
FROM mcr.microsoft.com/devcontainers/java:${JAVA_VERSION}-${DEBIAN_VARIANT}

# Create user
ARG USERNAME=minecraft
ARG USER_UID=1001
ARG USER_GID=${USER_UID}
RUN \
    groupadd --gid ${USER_GID} ${USERNAME} && \
    useradd --uid ${USER_UID} --gid ${USER_GID} -m ${USERNAME} && \
    echo ${USERNAME} ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/${USERNAME}

# Set cache
ENV GRADLE_USER_HOME=/gradle
RUN \
    mkdir -p ${GRADLE_USER_HOME} && \
    chown -R ${USERNAME}:${USERNAME} ${GRADLE_USER_HOME}

# Set user and working directory
USER $USERNAME
WORKDIR /src
