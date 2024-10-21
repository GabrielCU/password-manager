package com.example.passwordmanager.models.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class EncryptedKeyStorage {
    private static final String PREFS_NAME = "EncryptedKeys";
    private static final String ENCRYPTED_KEY = "EncryptedAESKey";

    public static void storeEncryptedAESKey(Context context, byte[] encryptedKey) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String encryptedKeyBase64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT);
        editor.putString(ENCRYPTED_KEY, encryptedKeyBase64);
        editor.apply();
    }

    public static byte[] getEncryptedAESKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String encryptedKeyBase64 = prefs.getString(ENCRYPTED_KEY, null);
        if (encryptedKeyBase64 != null) {
            return Base64.decode(encryptedKeyBase64, Base64.DEFAULT);
        } else {
            return null;
        }
    }
}
