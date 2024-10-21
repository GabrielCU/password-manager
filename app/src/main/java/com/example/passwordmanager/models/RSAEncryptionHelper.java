package com.example.passwordmanager.models;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PublicKey;

public class RSAEncryptionHelper {
    public static byte[] encryptAESKey(SecretKey aesKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // Use padding to match the key size
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(aesKey.getEncoded());
    }
}

