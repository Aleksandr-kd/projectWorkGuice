package ru.arutyunyan.dto;

import lombok.Getter;
import lombok.ToString;
import net.datafaker.Faker;

import java.util.Locale;


@ToString
@Getter
public class WishList {
    private final Faker faker = new Faker(new Locale("ru"));
    private final String productName = faker.commerce().productName();
    private final String description = faker.commerce().material();
}
