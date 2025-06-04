package ru.arutyunyan.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class AllureScreenshotUtils {

    public void takeScreenshot(WebDriver driver, String name) {
        if (driver instanceof TakesScreenshot screenshotTake) {
            byte[] screenshot = screenshotTake.getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment("Failure Screenshot: " + name,
                    "image/png",
                    "png", screenshot);
        }
    }
}