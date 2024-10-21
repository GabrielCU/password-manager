package com.example.passwordmanager.models;

public class Helper {

    public static int randomValue(int size) {
        double random = Math.random();
        return (int) (random * size);
    }

    public static int randomChar(int min, int max) {
        return randomValue(max - min + 1) + min;
    }
}
