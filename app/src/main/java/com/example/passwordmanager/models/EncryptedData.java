package com.example.passwordmanager.models;

public class EncryptedData {
    private byte[] iv;
    private byte[] ciphertext;

    public EncryptedData(byte[] iv, byte[] ciphertext) {
        this.iv = iv;
        this.ciphertext = ciphertext;
    }

    public byte[] getIv() {
        return iv;
    }

    public byte[] getCiphertext() {
        return ciphertext;
    }
}

