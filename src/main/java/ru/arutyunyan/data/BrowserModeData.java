package ru.arutyunyan.data;

public enum BrowserModeData {
    KIOSK("kiosk"),
    FULLSCREEN("fullscreen"),
    HEADLESS("headless");

    private final String name;

    BrowserModeData(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
