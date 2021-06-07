package com.waw_eve.seat.mumble.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AesUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CFB/PKCS5Padding";
    private static final String IV_STRING = "MUMBLE_AES_IVSTR";

    private static SecretKeySpec keySpec;
    private static IvParameterSpec ivSpec;

    private AesUtil() {
    }

    public static void init(Configuration configuration) {
        try {
            var kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            var random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(configuration.getAesEncryptKey().getBytes());
            kg.init(128, random);
            var secretKey = kg.generateKey();
            keySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
            ivSpec = new IvParameterSpec(IV_STRING.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("", ex);
        }
    }

    public static String encrypt(String content) {
        try {
            var cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] result = cipher.doFinal(Base64.decode(content));

            return Base64.toBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            logger.error("Encrypt message failed", e);
        }

        return null;
    }

    public static String decrypt(String content) {
        try {
            var cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] result = cipher.doFinal(Base64.decode(content));

            return Base64.toBase64String(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            logger.error("Decrypt message failed", e);
        }

        return null;
    }

}
