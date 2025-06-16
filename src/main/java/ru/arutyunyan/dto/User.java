package ru.arutyunyan.dto;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private final Faker faker = new Faker();

    private String name = faker.name().firstName();
    private String email = faker.internet().emailAddress();
    private String password = faker.internet().password();

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + (password != null ? "[PROTECTED]" : "null") + '\'' +
                '}';
    }
}