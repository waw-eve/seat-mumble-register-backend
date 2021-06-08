package com.waw_eve.seat.mumble.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptUtil {
    private static final Logger logger = LoggerFactory.getLogger(CryptUtil.class);

    private static String cipherAlgorithm;
    private static String iv;

    private static SecretKeySpec keySpec;

    private CryptUtil() {
    }

    static {
        // adds the Bouncy castle provider to java security
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void init(Configuration configuration) {
        keySpec = new SecretKeySpec(configuration.getEncryptKey().getBytes(StandardCharsets.UTF_8),
                configuration.getEncryptKey());
        cipherAlgorithm = configuration.getEncryptCipherAlgorithm();
        iv = configuration.getEncryptIV();
    }

    /**
     * Perform encryption and decryption operations
     * 
     * @param content base64 encoded plaintext
     * @param mode    Mode, support Cipher.DECRYPT_MODE and Cipher.ENCRYPT_MODE
     * @return base64 encoded ciphertext
     */
    public static String docrypt(String content, int mode) {
        try {
            var cipher = Cipher.getInstance(cipherAlgorithm);
            if (iv != null) {
                cipher.init(mode, keySpec, new IvParameterSpec(iv.getBytes()));
            } else {
                cipher.init(mode, keySpec);
            }
            byte[] result = cipher.doFinal(Base64.decode(content));

            return Base64.toBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            logger.error("Crypt message failed", e);
        }

        return null;
    }

}
