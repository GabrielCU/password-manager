package com.example.passwordmanager.models.generators;

public abstract class PasswordGenerator {

    protected int length;

    protected abstract char getChar();

    public PasswordGenerator(int length) {
        this.length = length;
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(getChar());
        }

        return password.toString();
    }
}
