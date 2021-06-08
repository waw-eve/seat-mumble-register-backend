package com.waw_eve.seat.mumble.utils;

import static org.junit.Assert.assertEquals;

import javax.crypto.Cipher;

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class CryptUtilTests {

    @Test
    public void aesUtilTest() {
        String src = Base64
                .toBase64String("{\"name\":\"test\",\"email\":\"test@test.com\",\"corp\":\"higgs\"}".getBytes());
        Configuration configuration = new Configuration();
        configuration.setEncryptKey("changeme");
        configuration.setEncryptIV("0123456789ABCDEF");
        configuration.setEncryptKeyAlgorithm("Blowfish");
        configuration.setEncryptCipherAlgorithm("Blowfish");
        CryptUtil.init(configuration);
        String encrypted = CryptUtil.docrypt(src, Cipher.ENCRYPT_MODE);
        assertEquals("vNDY5B9qmXJLqK57vYQ+PF5eZWzQPMoO2RBt40oLZE29r7Pd40NOodcQjZ7vGZvgUMOuf79BfB0=", encrypted);
        String decrypted = CryptUtil.docrypt(encrypted, Cipher.DECRYPT_MODE);
        assertEquals(src, decrypted);
    }
}
