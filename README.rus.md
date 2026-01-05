# Пустой мод

[English](./README.md) | Русский

[![Language](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](./LICENSE.txt)
[![Release](https://img.shields.io/github/v/release/imesense/mcforge1.12.2-empty-mod?include_prereleases&label=Release)](https://github.com/imesense/mcforge1.12.2-empty-mod/releases/latest)
[![Open in Dev Containers](https://img.shields.io/static/v1?label=Dev%20Containers&message=Open&color=blue&logo=visualstudiocode)](https://vscode.dev/redirect?url=vscode://ms-vscode-remote.remote-containers/cloneInVolume?url=https://github.com/imesense/mcforge1.12.2-empty-mod)
[![Build client](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-client.yml/badge.svg)](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-client.yml)
[![Build server](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-server.yml/badge.svg)](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-server.yml)
[![Build devcontainers](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-devcontainers.yml/badge.svg)](https://github.com/imesense/mcforge1.12.2-empty-mod/actions/workflows/build-devcontainers.yml)

Пустой мод для Minecraft 1.12.2 с поддержкой миксин.

## Требования

Для сборки:

- __JDK 1.8__,
- __Docker__ и __Docker Compose__.

Для разработки:

- __IntelliJ IDEA__ или __Visual Studio Code__.

## Сборка

### Клиент

- Собрать модификацию:

  ```sh
  ./gradlew build
  ```

### Сервер

- Собрать образ сервера:

  <!-- markdownlint-disable MD013 -->
  ```sh
  docker build --file docker/Server/Dockerfile --progress=plain --target final --tag mcforge1.12.2-empty-mod:latest .
  ```
  <!-- markdownlint-enable MD013 -->

  Или запустить скрипт сборки из папки `docker/Server/` в корне репозитория.

## Тестирование

- Запустить все тесты:

  ```sh
  ./gradlew test
  ```

## Установка

### Клиент

- Скопировать __JAR__ файлы из папки `build/libs/` в папку `mods/` игры Minecraft.

### Сервер

- Настроить `.env` и `server.properties` файлы.

  Смотрите примеры [`.env`](./docker/Server/.env.example) и [`server.properties`](./docker/Server/server.properties.example) в папке `docker/Server/`.

- Запустить команду для запуска сервера в контейнере:

  ```sh
  docker compose up -d
  ```

## Переменные окружения

Список переменных окружения, используемых контейнером сервера:

| Переменная          | Описание
| ------------------- | ---
| `IMAGE_REGISTRY`    | Реестр образа сервера.
| `IMAGE_TAG`         | Тег образа сервера.
| `EULA`              | Согласие с лицензией Minecraft.
| `SERVER_PORT`       | Порт сервера на хосте.
| `VOLUME_WORLD`      | Путь к папке `world/`.
| `VOLUME_CONFIG`     | Путь к папке `config/`.
| `VOLUME_REPORTS`    | Путь к папке `crash-reports/`.
| `VOLUME_PROPERTIES` | Путь к конфигу `server.properties`.

## Лицензия

Содержимое данного репозитория лицензировано согласно условиям __лицензии MIT__, пока не указано иное.
Смотрите [этот](./LICENSE.txt) файл для деталей.
