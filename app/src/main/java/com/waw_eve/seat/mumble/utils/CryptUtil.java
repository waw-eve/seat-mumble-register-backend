package com.waw_eve.seat.mumble.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptUtil {
    private static final Logger logger = LoggerFactory.getLogger(CryptUtil.class);

    private static final String KEY_ALGORITHM = "Blowfish";
    private static final String DEFAULT_CIPHER_ALGORITHM = "Blowfish";

    private static SecretKeySpec keySpec;

    private CryptUtil() {
    }

    public static void init(Configuration configuration) {
        // adds the Bouncy castle provider to java security
        Security.addProvider(new BouncyCastleProvider());
        keySpec = new SecretKeySpec(configuration.getEncryptKey().getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);
    }

    public static String docrypt(String content, int mode) {
        try {
            var cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(mode, keySpec);
            byte[] result = cipher.doFinal(Base64.decode(content));

            return Base64.toBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            logger.error("Crypt message failed", e);
        }

        return null;
    }

}
