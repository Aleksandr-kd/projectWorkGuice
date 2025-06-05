package ru.arutyunyan.dto;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.util.Locale;


public class WishList {

    private final Faker faker = new Faker(new Locale("ru"));

    @Getter
    private final String productName = faker.commerce().productName();
    @Getter
    private final String price = faker.commerce().price();
    @Getter
    private final String description = faker.commerce().material();
    @Getter
    private final String descriptionProduct = String.format(
            "Это описание и сам товар: %s могут не совпадать по логике друг с другом: %s",
            productName, description
    );
}
