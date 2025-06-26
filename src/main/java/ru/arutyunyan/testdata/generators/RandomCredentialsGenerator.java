package ru.arutyunyan.testdata.generators;

import java.security.SecureRandom;
import java.util.Random;


public class RandomCredentialsGenerator {
    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String EMAIL_DOMAINS = "gmail.com,yahoo.com,outlook.com,example.com,mail.com,mail.ru";

    private final Random random = new SecureRandom();

    public String generateLogin(int minLength, int maxLength) {
        return generateRandomString(LOWERCASE_CHARS + UPPERCASE_CHARS + NUMBERS, minLength, maxLength);
    }

    public String generateEmail(int minLength, int maxLength) {
        String name = generateRandomString(LOWERCASE_CHARS + NUMBERS, minLength, maxLength);
        String[] domains = EMAIL_DOMAINS.split(",");
        String domain = domains[random.nextInt(domains.length)];
        return name + "@" + domain;
    }

    public String generatePassword(int minLength, int maxLength) {
        return generateRandomString(LOWERCASE_CHARS + UPPERCASE_CHARS + NUMBERS + SPECIAL_CHARS, minLength, maxLength);
    }

    private String generateRandomString(String characters, int minLength, int maxLength) {
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }
}