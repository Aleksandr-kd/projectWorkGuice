package ru.arutyunyan.testdata.factories;

import ru.arutyunyan.dto.User;
import ru.arutyunyan.testdata.generators.RandomCredentialsGenerator;

public class UserFactory {
    private final RandomCredentialsGenerator generator = new RandomCredentialsGenerator();

    public User generate() {
        return new User(
                generator.generateLogin(6, 12),
                generator.generateEmail(6, 12),
                generator.generatePassword(8, 16)
        );
    }
}