package com.waw_eve.seat.mumble;

import lombok.Data;

@Data
public class Configuration {
    /**
     * If ca file exist, use this to decrypt p12 file.
     * 
     * If ca file not exist, use this to encrypt p12 file
     */
    private String caPassword = "";

    /**
     * CA file, must endwith .p12
     */
    private String caFilePath = "ca.p12";

    /**
     * AES encrypt key use to encrypt http request data
     */
    private String aesEncryptKey = "changeme";

    /**
     * If ca file not exist, use this name as CA's CN
     */
    private String caSubject = "C=CN, O=Test, OU=Root CA, CN=Test Root CA";

    /**
     * The listening address of the mumble instance to connect to
     */
    private String mumbleIceAddr = "tcp -h 127.0.0.1 -p 6502";

    /**
     * Write secret for mumble server
     */
    private String mumbleIceSecret = "changeme";
}
