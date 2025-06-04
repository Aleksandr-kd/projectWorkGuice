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
}
