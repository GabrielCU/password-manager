package com.example.passwordmanager.models.generators;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecurePasswordGenerator extends PasswordGenerator {

    private static final String UPPERCASE    = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE    = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS      = "0123456789";
    private static final String SPECIAL_CHARS= "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
    private static final String ALL_CHARS    = UPPERCASE + LOWERCASE + NUMBERS + SPECIAL_CHARS;
    private final SecureRandom random;

    public SecurePasswordGenerator(int length) {
        super(length);
        this.random = new SecureRandom();
    }

    @Override
    protected char getChar() {
        return ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
    }

    @Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder(length);

        // Ensure at least one character from each category
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        return shuffleString(password.toString());
    }

    private String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters, random);
        StringBuilder output = new StringBuilder(input.length());
        for (char c : characters) {
            output.append(c);
        }
        return output.toString();
    }
}
