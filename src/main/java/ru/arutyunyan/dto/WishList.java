package ru.arutyunyan.dto;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.util.Locale;

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

    @Override
    public String toString() {
        return "WishList{" +
                "productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", descriptionProduct='" + descriptionProduct + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
