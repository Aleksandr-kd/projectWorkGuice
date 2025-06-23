package ru.arutyunyan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.datafaker.Faker;


@ToString
@Getter
@Setter
public class User {
    private final Faker faker = new Faker();
    private String name = faker.name().firstName();
    private String email = faker.internet().emailAddress();
    private String password = faker.internet().password();
}