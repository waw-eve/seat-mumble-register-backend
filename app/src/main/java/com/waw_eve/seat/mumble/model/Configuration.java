package com.waw_eve.seat.mumble.model;

import lombok.Data;

@Data
public class Configuration {
    /**
     * CA file, must endwith .p12
     */
    private String caFilePath = "ca.p12";
    /**
     * If ca file exist, use this to decrypt p12 file.
     * 
     * If ca file not exist, use this to encrypt p12 file
     */
    private String caPassword = "";
    /**
     * If ca file not exist, use this name as CA's subject.
     */
    private String caSubject = "C=CN, O=Test, OU=Root CA, CN=Test Root CA";

    /**
     * Encrypt key use to encrypt http request data
     */
    private String encryptKey = "changeme";
    /**
     * Initial vector, only available when using supported algorithms
     */
    private String encryptIV = "0123456789ABCDEF";
    /**
     * Encryption key algorithm
     */
    private String encryptKeyAlgorithm = "Blowfish";
    /**
     * Encryption cipher algorithm
     */
    private String encryptCipherAlgorithm = "Blowfish";

    /**
     * Mumble server's ice configuration
     */
    private String mumbleIceAddr = "tcp -h 127.0.0.1 -p 6502";

    /**
     * Write secret for mumble server
     */
    private String mumbleIceSecret = "changeme";

}
