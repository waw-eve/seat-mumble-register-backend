package com.waw_eve.seat.mumble.utils;

import static org.junit.Assert.assertEquals;
import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class AesUtilTests {

    @Test
    public void aesUtilTest() {
        String src = Base64.toBase64String("test".getBytes());
        Configuration configuration = new Configuration();
        configuration.setAesEncryptKey("meow");
        AesUtil.init(configuration);
        String encrypted = AesUtil.encrypt(src);
        String decrypted = AesUtil.decrypt(encrypted);
        assertEquals(src, decrypted);
    }
}
