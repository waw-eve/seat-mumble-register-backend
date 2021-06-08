package com.waw_eve.seat.mumble.utils;

import static org.junit.Assert.assertEquals;

import javax.crypto.Cipher;

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class CryptUtilTests {

    @Test
    public void aesUtilTest() {
        String src = Base64.toBase64String("testaaaaaaaaaa".getBytes());
        Configuration configuration = new Configuration();
        configuration.setEncryptKey("meow");
        CryptUtil.init(configuration);
        String encrypted = CryptUtil.docrypt(src, Cipher.ENCRYPT_MODE);
        assertEquals("8v3sYLHNLipjNsO0kExuZA==", encrypted);
        String decrypted = CryptUtil.docrypt(encrypted, Cipher.DECRYPT_MODE);
        assertEquals(src, decrypted);
    }
}
