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
    private final String price = faker.commerce().price();
    private final String description = faker.commerce().material();
    private final String descriptionProduct = String.format(
            "Это описание и сам товар: %s могут не совпадать по логике друг с другом: %s",
            productName, description
    );
}
