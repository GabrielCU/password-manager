package com.example.passwordmanager.models;

import java.security.SecureRandom;

public class InsecurePasswordGenerator extends PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private final SecureRandom random;

    public InsecurePasswordGenerator(int length) {
        super(length);
        this.random = new SecureRandom();
    }

    @Override
    protected char getChar() {
        return LOWERCASE.charAt(random.nextInt(LOWERCASE.length()));
    }

}
