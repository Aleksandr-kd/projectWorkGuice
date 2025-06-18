package ru.arutyunyan.data;

import lombok.Getter;


@Getter
public enum BrowserModeData {
    KIOSK("kiosk"),
    FULLSCREEN("fullscreen"),
    HEADLESS("headless");

    private final String name;

    BrowserModeData(String name) {
        this.name = name;
    }
}
