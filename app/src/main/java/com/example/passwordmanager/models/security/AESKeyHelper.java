package com.example.passwordmanager.models.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESKeyHelper {
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
}

