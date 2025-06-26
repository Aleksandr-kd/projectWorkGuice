//package ru.arutyunyan.factory;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import ru.arutyunyan.exceptions.BrowserNotSupportedException;
//import ru.arutyunyan.factory.settings.ChromeSettings;
//import ru.arutyunyan.factory.settings.FirefoxSettings;
//
//
//public class WebDriverFactory {
//
//    private final String browserName = System.getProperty("browser");
//
//    public WebDriver createDriver() {
//        return switch (browserName.toLowerCase()) {
//            case "chrome" -> new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
//            case "firefox" -> new FirefoxDriver((FirefoxOptions) new FirefoxSettings().settings());
//            default -> throw new BrowserNotSupportedException(browserName);
//        };
//    }
//}
package ru.arutyunyan.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.arutyunyan.exceptions.BrowserNotSupportedException;
import ru.arutyunyan.factory.settings.ChromeSettings;
import ru.arutyunyan.factory.settings.FirefoxSettings;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

    private final String browserName = System.getProperty("browser");

    public WebDriver createDriver() {
        String mode = System.getProperty("mode", "local");

        if ("grid".equalsIgnoreCase(mode)) {
            String url = System.getProperty("grid.url");
            try {
                return switch (browserName.toLowerCase()) {
                    case "chrome" -> new RemoteWebDriver(
                            new URL(url),
                            new ChromeSettings().settings()
                    );
                    case "firefox" -> new RemoteWebDriver(
                            new URL(url),
                            new FirefoxSettings().settings()
                    );
                    default -> throw new BrowserNotSupportedException(browserName);
                };
            } catch (MalformedURLException e) {
                throw new RuntimeException("Невалидный URL для Selenium Grid: " + url, e);
            }
        }

        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeDriver(
                    (ChromeOptions) new ChromeSettings().settings()
            );
            case "firefox" -> new FirefoxDriver(
                    (FirefoxOptions) new FirefoxSettings().settings()
            );
            default -> throw new BrowserNotSupportedException(browserName);
        };
    }
}
