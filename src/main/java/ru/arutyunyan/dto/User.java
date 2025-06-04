package ru.arutyunyan.dto;
import com.github.javafaker.Faker;


public class User {

    private final Faker faker = new Faker();

    @Getter
    private final String name = faker.name().firstName();
    @Getter
    private final String email = faker.internet().emailAddress();
    @Getter
    private final String password = faker.internet().password();

    public Faker getFaker() {
        return faker;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
