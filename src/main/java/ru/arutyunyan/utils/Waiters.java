package ru.arutyunyan.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;


public class Waiters {

    private final WebDriver driver;

    private WebDriverWait wait;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
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

    public WebElement waitForElementVisible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPageLoad() {
        getWait().until(d ->
                ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    public boolean waitForCondition(ExpectedCondition condition) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(condition);
            return true;
        } catch (TimeoutException ignored) {
            return false;
        }

                // Ожидание загрузки страницы
//        public static void waitForPageLoad(WebDriver driver) {
//            waitForPageLoad(driver, DEFAULT_TIMEOUT);
//        }

//        // Ожидание видимости элемента
//        public static WebElement waitForElementVisible(WebDriver driver, By locator) {
//            return waitForElementVisible(driver, locator, DEFAULT_TIMEOUT);
//        }

//        public static WebElement waitForElementVisible(WebDriver driver, By locator, long timeoutInSeconds) {
//            return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
//                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
//        }

        // Ожидание кликабельности элемента
//        public static WebElement waitForElementClickable(WebDriver driver, By locator) {
//            return waitForElementClickable(driver, locator, DEFAULT_TIMEOUT);
//        }

//        public static WebElement waitForElementClickable(WebDriver driver, By locator, long timeoutInSeconds) {
//            return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
//                    .until(ExpectedConditions.elementToBeClickable(locator));
//        }

//        // Дополнительные полезные методы
//        public static void waitForTextPresent(WebDriver driver, By locator, String text) {
//            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
//                    .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
//        }

//        public static void waitForElementsVisible(WebDriver driver, By locator) {
//            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
//                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
//        }
//
//        public static void waitForInvisibility(WebDriver driver, By locator) {
//            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
//                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
//        }
//    }
//    }
//
//    public boolean waitForElementShouldBeVisible(By locator) {
//        return waitForCondition(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//
//    public boolean waitForElementShouldNotBeVisible(By locator) {
//        return waitForCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
//    private WebDriverWait getWait() {
//        return new WebDriverWait(driver, DEFAULT_TIMEOUT);
//    }


    /**
     * Ждёт, пока текст в элементе ИЗМЕНИТСЯ с начального значения
     */
    public void waitUntilTextChanges(By locator, String initialText) {
        getWait().until(driver -> {
            String currentText = driver.findElement(locator).getText();
            return !currentText.equals(initialText) ? currentText : null;
        });
    }
    // Основные методы ожидания
//        PageFactory.initElements(driver, this);

    protected WebElement waitForElement(By locator) {
        return getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Ожидание по уже найденному WebElement (новый вариант)
    protected WebElement waitForElement(WebElement element) {
        return getFluentWait().until(ExpectedConditions.visibilityOf(element));
    }



    protected WebElement waitForElement(By locator, Duration timeout) {
        return getFluentWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForAllElements(By locator) {
        return getFluentWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        return getFluentWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected Boolean waitForElementToDisappear(By locator) {
        return getFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected Boolean waitForTextToBePresentInElement(By locator, String text) {
        return getFluentWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    //Alert alert = wait.until(ExpectedConditions.alertIsPresent());

    @Step("Ожидание загрузки страницы")
    protected void waitPageLoad() {
        getFluentWait().until(d ->
                ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    @Step("Ожидание загрузки AJAX (jQuery)")
    protected void waitForJQueryLoad() {
        getFluentWait().until(d ->
                (Boolean) ((JavascriptExecutor) d).executeScript(
                        "return (typeof jQuery != 'undefined') ? jQuery.active == 0 : true"));
    }

    // Настройка FluentWait
    private FluentWait<WebDriver> getFluentWait() {
        return getFluentWait(DEFAULT_TIMEOUT);
    }

    private FluentWait<WebDriver> getFluentWait(Duration timeout) {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    // Дополнительные полезные методы

    @Step("Ожидание и клик по элементу")
    public void waitAndClick(WebElement element) {
        waitForElementToBeClickable(element).click();
    }

    @Step("Ожидание и ввод текста")
    protected void waitAndSendKeys(WebElement element, String text) {
        waitForElement(element);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Ожидание и получение текста")
    public String waitAndGetText(WebElement element) {
        return waitForElement(element).getText();
    }

    @Step("Проверка видимости элемента")
    protected void isElementVisible(By locator) {
        try {
            waitForElement(locator, Duration.ofSeconds(10)).isDisplayed();
        } catch (TimeoutException ignored) {
        }
    }
    // Ожидание появления alert
    public Alert waitForAlert() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public WebElement waitFor(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    // Заполнение и подтверждение prompt
    public void fillAndAcceptPrompt(String text) {
        Alert alert = waitForAlert();
        alert.sendKeys(text);
        alert.accept();
    }

    // Простое подтверждение alert
    public void acceptAlert() {
        waitForAlert().accept();
    }

    public void waitPageLoad(int timeoutInSeconds) {
        getFluentWait(Duration.ofDays(timeoutInSeconds))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
}