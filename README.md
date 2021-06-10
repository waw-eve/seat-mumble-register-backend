# seat-mumble-register-backend
[![CI to Docker Hub](https://github.com/waw-eve/seat-mumble-register-backend/actions/workflows/main.yml/badge.svg)](https://github.com/waw-eve/seat-mumble-register-backend/actions/workflows/main.yml)
[![Docker image size](https://img.shields.io/docker/image-size/alliancewaw/seat-mumble-register)](https://hub.docker.com/r/alliancewaw/seat-mumble-register)

SeAT's mumble registration plugin backend

# Introduction
This program provides an http interface.

It accepts the base64 encoding of the request encrypted by the encryption algorithm specified in the configuration file, and generates and registers the mumble user's certificate according to the provided information.

Return the base64 encoding of the certificate pfx file encrypted in the same way.
