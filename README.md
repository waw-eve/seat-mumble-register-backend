# seat-mumble-register-backend
SeAT's mumble registration plugin backend

# Introduction
This program provides an http interface.

It accepts the base64 encoding of the request encrypted by the encryption algorithm specified in the configuration file, and generates and registers the mumble user's certificate according to the provided information.

Return the base64 encoding of the certificate pfx file encrypted in the same way.