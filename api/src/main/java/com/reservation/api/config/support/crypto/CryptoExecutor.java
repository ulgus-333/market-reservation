package com.reservation.api.config.support.crypto;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CryptoExecutor {
    private static final Argon2PasswordEncoder PASSWORD_ENCODER = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Value("${crypto.secret-key}")
    private String secretKey;

    private static SecretKeySpec KEY_SPEC;

    @PostConstruct
    public void init() {
        KEY_SPEC = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
    }

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");;

            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, KEY_SPEC, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedIvAndText = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedIvAndText, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedIvAndText, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedIvAndText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedIvText) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedIvText);

            byte[] iv = new byte[16];
            byte[] encrypted = new byte[decoded.length - 16];

            System.arraycopy(decoded, 0, iv, 0, 16);
            System.arraycopy(decoded, 16, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, KEY_SPEC, ivSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptPassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    public static Boolean verifyPassword(String password, String encryptedPassword) {
        return PASSWORD_ENCODER.matches(password, encryptedPassword);
    }
}
