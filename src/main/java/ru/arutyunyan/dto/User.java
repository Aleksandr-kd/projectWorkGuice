package ru.arutyunyan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.datafaker.Faker;


@ToString
@Getter
@AllArgsConstructor
public class User {
    private final Faker faker = new Faker();
    private final String name = faker.name().firstName().replaceAll("[^a-zA-Z]", "");
    private final String email = faker.internet().emailAddress();
    private final String password = faker.regexify("[a-zA-Z0-9]{8,16}");
}