package ru.arutyunyan.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.arutyunyan.utils.Waiters;

import java.util.List;


public class AbsCommon {

    protected WebDriver driver = null;
    protected Waiters waiters;

    public AbsCommon(WebDriver driver) {
        this.driver = driver;
        this.waiters = new Waiters(driver);
    }

    public WebElement $(By locator){
        return driver.findElement(locator);
    }

    public List<WebElement> $$(By locator){
        return driver.findElements(locator);
    }
}
