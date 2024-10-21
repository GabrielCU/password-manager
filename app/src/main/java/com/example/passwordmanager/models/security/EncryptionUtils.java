package com.example.passwordmanager.models.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class EncryptionUtils {
    private static final String AES_MODE = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16; // 16 bytes IV for AES

    public static EncryptedData encrypt(String plaintext, SecretKey aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        byte[] iv = generateIV();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));
        return new EncryptedData(iv, ciphertext);
    }

    public static String decrypt(EncryptedData encryptedData, SecretKey aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        IvParameterSpec ivSpec = new IvParameterSpec(encryptedData.getIv());
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);

        byte[] plaintext = cipher.doFinal(encryptedData.getCiphertext());
        return new String(plaintext, "UTF-8");
    }

    private static byte[] generateIV() {
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}

