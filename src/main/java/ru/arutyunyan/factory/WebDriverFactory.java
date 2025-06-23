package ru.arutyunyan.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.arutyunyan.exceptions.BrowserNotSupportedException;
import ru.arutyunyan.factory.settings.ChromeSettings;
import ru.arutyunyan.factory.settings.FirefoxSettings;


public class WebDriverFactory {

    private final String browserName = System.getProperty("browser");

    public WebDriver createDriver() {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
            case "firefox" -> new FirefoxDriver((FirefoxOptions) new FirefoxSettings().settings());
            default -> throw new BrowserNotSupportedException(browserName);
        };
    }
}
