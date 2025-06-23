package ru.arutyunyan.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Waiters {

    private final WebDriver driver;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(50);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);

    public Waiters(WebDriver driver) {
        this.driver = driver;
    }

    private FluentWait<WebDriver> getWait() {
        return new FluentWait<>(driver)
                .withTimeout(DEFAULT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    protected WebElement waitForElement(WebElement element) {
        return getFluentWait().until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        return getFluentWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected FluentWait<WebDriver> getFluentWait() {
        return getFluentWait(DEFAULT_TIMEOUT);
    }

    protected FluentWait<WebDriver> getFluentWait(Duration timeout) {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @Step("Ожидание и клик по элементу")
    public void waitAndClick(WebElement element) {
        waitForElementToBeClickable(element).click();
    }

    @Step("Ожидание и получение текста")
    public String waitAndGetText(WebElement element) {
        return waitForElement(element).getText();
    }

    @Step("Ожидание видимости элемента")
    public WebElement waitForElementVisible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    @Step("Ожидание исчезновения элемента")
    public void waitForElementInVisible(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    @Step("Ожидание видимости элемента")
    public boolean waitForElementVisible(WebElement element, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Ожидание загрузки страницы")
    public void waitForPageLoad() {
        getWait().until(d ->
                ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }
}