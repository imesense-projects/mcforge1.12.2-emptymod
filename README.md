# Empty Mod

English | [Русский](./README.rus.md)

[![Language](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](./LICENSE.txt)
[![Release](https://img.shields.io/github/v/release/imesense/mcforge1.12.2-empty-mod?include_prereleases&label=Release)](https://github.com/imesense/mcforge1.12.2-empty-mod/releases/latest)
[![Open in Dev Containers](https://img.shields.io/static/v1?label=Dev%20Containers&message=Open&color=blue&logo=visualstudiocode)](https://vscode.dev/redirect?url=vscode://ms-vscode-remote.remote-containers/cloneInVolume?url=https://github.com/imesense/mcforge1.12.2-empty-mod)
[![Build client](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-client.yml/badge.svg)](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-client.yml)
[![Build server](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-server.yml/badge.svg)](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-server.yml)
[![Build devcontainers](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-devcontainers.yml/badge.svg)](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-devcontainers.yml)

Empty mod for Minecraft 1.12.2 with mixins support.

## Requirements

For building:

- __JDK 1.8__,
- __Docker__ and __Docker Compose__.

For development:

- __IntelliJ IDEA__ or __Visual Studio Code__.

## Building

### Client

- Build modification:

  ```sh
  ./gradlew build
  ```

### Server

- Build server image:

  <!-- markdownlint-disable MD013 -->
  ```sh
  docker build --file docker/Server/Dockerfile --progress=plain --target final --tag mcforge1.12.2-empty-mod:latest .
  ```
  <!-- markdownlint-enable MD013 -->

  Or run build script from `docker/Server/` folder in root of the repository.

## Testing

- Launch all tests:

  ```sh
  ./gradlew test
  ```

## Installation

### Client

- Copy __JAR__ files from `build/libs/` to `mods/` folder of Minecraft.

### Server

- Configure `.env` and `server.properties` files.

  See [`.env`](./docker/Server/.env.example) and [`server.properties`](./docker/Server/server.properties.example) examples in `docker/Server/` folder.

- Run command to run server in container:

  ```sh
  docker compose up -d
  ```

## Environment Variables

List of environment variables used by server container:

| Variable            | Description
| ------------------- | ---
| `IMAGE_REGISTRY`    | Server image registry.
| `IMAGE_TAG`         | Server image tag.
| `EULA`              | Minecraft license agreement.
| `SERVER_PORT`       | Server port on the host.
| `VOLUME_WORLD`      | Path to `world/` folder.
| `VOLUME_CONFIG`     | Path to `config/` folder.
| `VOLUME_REPORTS`    | Path to `crash-reports/` folder.
| `VOLUME_PROPERTIES` | Path to `server.properties` config.

## License

Contents of this repository licensed under terms of the __MIT license__ unless otherwise specified.
See [this](./LICENSE.txt) file for details.
