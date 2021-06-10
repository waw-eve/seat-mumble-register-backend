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
        String src = Base64.toBase64String(
                "{\"name\":\"Ielenia Amastacia\",\"email\":\"ieleniaamastacia@waw-eve.com\"}".getBytes());
        Configuration configuration = new Configuration();
        configuration.setEncryptKey("changeme");
        Config.init(configuration);
        CryptUtil.init();
        String encrypted = CryptUtil.docrypt(src, Cipher.ENCRYPT_MODE);
        assertEquals("vNDY5B9qmXL/YkfcqdUovbeztOpyQaXzY7+asQ2Uk3FcPQv2fadYsTAmeXYeJaeu6O5/97Wu5EYrIfLI8dm0/3tJ1eQDdvqG",
                encrypted);
        String decrypted = CryptUtil.docrypt(encrypted, Cipher.DECRYPT_MODE);
        assertEquals(src, decrypted);
    }
}
