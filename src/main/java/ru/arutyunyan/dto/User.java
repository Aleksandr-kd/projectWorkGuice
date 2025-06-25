package ru.arutyunyan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.datafaker.Faker;


@ToString
@Getter
@AllArgsConstructor
public class User {
    private final String name;
    private final String email;
    private final String password;
//    private final Faker faker = new Faker();
//    private String name = faker.name().firstName();
//    private String email = faker.internet().emailAddress();
//    private String password = faker.text().text(8, 16);
}