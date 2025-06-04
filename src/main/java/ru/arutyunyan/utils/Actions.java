package ru.arutyunyan.utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


public class Actions {

    private final WebDriver driver;

    public Actions(WebDriver driver) {
        this.driver = driver;
    }

    public void pause(long millis) {
        new org.openqa.selenium.interactions.Actions(driver)
                .pause(millis)
                .perform();
    }

    public void sendEnd(long millisStart, long millisEnd) {
        new org.openqa.selenium.interactions.Actions(driver)
                .pause(millisStart)
                .sendKeys(Keys.END)
                .pause(millisEnd)
                .perform();

    }

}
