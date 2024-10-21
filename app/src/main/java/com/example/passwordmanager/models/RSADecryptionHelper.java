package com.example.passwordmanager.models;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSADecryptionHelper {
    public static SecretKey decryptAESKey(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // Use matching padding
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);
        return new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
    }
}

