package com.example.passwordmanager.models.security;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;

import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

public class KeyStoreManager {
    private static final String KEY_ALIAS = "MyKeyAlias";
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";

    public static void generateKeyPair(Context context) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 25);

            KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                    .setAlias(KEY_ALIAS)
                    .setSubject(new X500Principal("CN=" + KEY_ALIAS))
                    .setSerialNumber(BigInteger.ONE)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();

            KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance("RSA", ANDROID_KEYSTORE);
            kpGenerator.initialize(spec);

            KeyPair kp = kpGenerator.generateKeyPair();
        }
    }

    public static PrivateKey getPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
        return privateKeyEntry.getPrivateKey();
    }

    public static PublicKey getPublicKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
        return privateKeyEntry.getCertificate().getPublicKey();
    }
}

