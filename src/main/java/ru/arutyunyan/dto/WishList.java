package ru.arutyunyan.dto;

import com.github.javafaker.Faker;


import java.util.Locale;


public class WishList {

    private final Faker faker = new Faker(new Locale("ru"));

    private final String productName = faker.commerce().productName();
    private final String price = faker.commerce().price();
    private final String description = faker.commerce().material();
    private final String descriptionProduct = String.format(
            "Это описание и сам товар: %s могут не совпадать по логике друг с другом: %s",
            productName, description
    );

    public Faker getFaker() {
        return faker;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }
}
