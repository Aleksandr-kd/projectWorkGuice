package ru.arutyunyan.dto;

import net.datafaker.Faker;

public class UserFactory {

    private static final Faker faker = new Faker();

    public static User generateUser() {
        String name = faker.name().firstName().replaceAll("[^a-zA-Z]", "");
        String email = faker.internet().emailAddress();
        String password = faker.regexify("[a-zA-Z0-9]{8,16}");
        return new User(name, email, password);
    }

    public static User mutateUser(User baseUser, String suffix) {
        String newName = baseUser.getName() + suffix;
        String newEmail = baseUser.getEmail().replace("@", suffix + "@");
        String newPassword = baseUser.getPassword() + suffix;
        return new User(newName, newEmail, newPassword);
    }
}