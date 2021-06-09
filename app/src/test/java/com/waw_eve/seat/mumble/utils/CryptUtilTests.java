package com.waw_eve.seat.mumble.utils;

import static org.junit.Assert.assertEquals;

import javax.crypto.Cipher;

import com.waw_eve.seat.mumble.Config;
import com.waw_eve.seat.mumble.model.Configuration;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class CryptUtilTests {

    @Test
    public void cryptUtilTest() {
        String src = Base64
                .toBase64String("{\"name\":\"test\",\"email\":\"test@test.com\",\"corp\":\"higgs\"}".getBytes());
        Configuration configuration = new Configuration();
        configuration.setEncryptKey("changeme");
        configuration.setEncryptIV("0123456789ABCDEF");
        configuration.setEncryptKeyAlgorithm("Blowfish");
        configuration.setEncryptCipherAlgorithm("Blowfish");
        Config.init(configuration);
        CryptUtil.init();
        String encrypted = CryptUtil.docrypt(src, Cipher.ENCRYPT_MODE);
        assertEquals("vNDY5B9qmXJLqK57vYQ+PF5eZWzQPMoO2RBt40oLZE29r7Pd40NOodcQjZ7vGZvgUMOuf79BfB0=", encrypted);
        String decrypted = CryptUtil.docrypt(encrypted, Cipher.DECRYPT_MODE);
        assertEquals(src, decrypted);
    }
}
