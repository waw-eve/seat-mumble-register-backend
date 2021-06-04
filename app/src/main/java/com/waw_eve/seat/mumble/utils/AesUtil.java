package com.waw_eve.seat.mumble.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static SecretKeySpec aesSecretKey;

    public static void init(Configuration configuration) {
        aesSecretKey = getSecretKey(configuration.getAesEncryptKey());
    }

    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
            byte[] result = cipher.doFinal(Base64.decode(content));

            return Base64.toBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            logger.error("Encrypt message failed", e);
        }

        return null;
    }

    public static String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, aesSecretKey);
            byte[] result = cipher.doFinal(Base64.decode(content));

            return Base64.toBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            logger.error("Decrypt message failed", e);
        }

        return null;
    }

    private static SecretKeySpec getSecretKey(String aesEncryptKey) {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesEncryptKey.getBytes());
            kg.init(128, random);
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("", ex);
        }
        return null;
    }
}
