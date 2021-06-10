package com.waw_eve.seat.mumble.model;

import lombok.Data;

@Data
public class Configuration {
    /**
     * Encrypt key use to encrypt http request data
     */
    private String encryptKey = "changeme";

    /**
     * Mumble server's ice configuration
     */
    private String mumbleIceAddr = "tcp -h 127.0.0.1 -p 6502";

    /**
     * Write secret for mumble server
     */
    private String mumbleIceSecret = "changeme";

}
