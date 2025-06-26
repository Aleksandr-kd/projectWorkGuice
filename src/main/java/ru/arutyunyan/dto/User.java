package ru.arutyunyan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class User {
    private final String name;
    private final String email;
    private final String password;
}