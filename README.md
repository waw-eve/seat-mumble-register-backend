# seat-mumble-register-backend

[![CI to Docker Hub](https://github.com/waw-eve/seat-mumble-register-backend/actions/workflows/main.yml/badge.svg)](https://github.com/waw-eve/seat-mumble-register-backend/actions/workflows/main.yml)
[![Docker image size](https://img.shields.io/docker/image-size/alliancewaw/seat-mumble-register)](https://hub.docker.com/r/alliancewaw/seat-mumble-register)

SeAT's mumble registration plugin backend

## Introduction

This program provides an http interface.

It accepts the base64 encoding of the request encrypted by the encryption algorithm specified in the configuration file, and generates and registers the mumble user's certificate according to the provided information.

Return the base64 encoding of the certificate pfx file encrypted in the same way.

## Getting started

### Installation

Automated builds of the image are available on [Dockerhub](https://hub.docker.com/r/alliancewaw/seat-mumble-register) and is the recommended method of installation.

```bash
docker pull alliancewaw/seat-mumble-register
```

### Quickstart

you can use the sample [docker-compose.yml](docker-compose.yml) file to start the container using [Docker Compose](https://docs.docker.com/compose/)

### Configuration

After first run, it will auto create config.json file in data folder.

Edit the config.json to change settings.

This is sample config

```json
{
  "encryptKey": "changeme",
  "mumbleIceAddr": "tcp -h 127.0.0.1 -p 6502",
  "mumbleIceSecret": "changeme"
}
```
