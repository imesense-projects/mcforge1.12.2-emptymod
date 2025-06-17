# Empty Mod

<div>
  <p>
    <a href="./LICENSE">
      <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License" />
    </a>
    <a href="https://github.com/imesense/mcforge1.12.2-emptymod/actions/workflows/build-client.yml">
      <img src="https://github.com/imesense/mcforge1.12.2-emptymod/actions/workflows/build-client.yml/badge.svg" alt="Build client" />
    </a>
    <a href="https://github.com/imesense/mcforge1.12.2-emptymod/actions/workflows/build-server.yml">
      <img src="https://github.com/imesense/mcforge1.12.2-emptymod/actions/workflows/build-server.yml/badge.svg" alt="Build server" />
    </a>
  </p>
</div>

Empty mod for Minecraft 1.12.2 with mixins support

## Requirements

- JDK 1.8
- IntelliJ IDEA/Visual Studio Code

## Building

### Client

- Run command in root of the repository to build modification:

  ```sh
  ./gradlew build
  ```

### Server

- Run command to build server image:

  ```sh
  docker build --file docker/Server/Dockerfile --progress=plain --target final --tag mcforge1.12-emptymod:latest .
  ```

  Or run build script from `docker/Server/` folder in root of the repository

## Installation

### Client

- Copy JAR files from `build/libs/` to `mods/` folder of Minecraft

### Server

- Configure `.env` and `server.properties` files

  See [.env](./docker/Server/.env.example) and [server.properties](./docker/Server/server.properties.example) examples in `docker/Server/` folder

- Run command to run server in container:

  ```sh
  docker compose up -d
  ```

## Environment variables

List of environment variables used by server container

| Variable          | Description
| ----------------- | ---
| IMAGE_REGISTRY    | Server image registry
| IMAGE_TAG         | Server image tag
| EULA              | Minecraft license agreement
| SERVER_PORT       | Server port on the host
| VOLUME_WORLD      | Path to `world/` folder
| VOLUME_CONFIG     | Path to `config/` folder
| VOLUME_REPORTS    | Path to `crash-reports/` folder
| VOLUME_PROPERTIES | Path to `server.properties` config

## License

Contents of this repository licensed under terms of the __MIT license__ unless otherwise specified. See [this](./LICENSE.txt) file for details
