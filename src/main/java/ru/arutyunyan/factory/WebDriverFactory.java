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
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;


public class WebDriverFactory {

    private final String browserName = System.getProperty("browser");
    private final String browserVersion = System.getProperty("browserVersion");
    private String remoteUrl = System.getProperty("remote.url", "");

    public WebDriver createDriver() throws MalformedURLException {
        if (!remoteUrl.isEmpty()) {
            MutableCapabilities capabilities = new MutableCapabilities();
            capabilities.setCapability("browserName", browserName);
            capabilities.setCapability("browserVersion", browserVersion);

            MutableCapabilities options;
            switch (browserName.toLowerCase()) {
                case "chrome" -> options = (ChromeOptions) new ChromeSettings().settings();
                case "firefox" -> options = (FirefoxOptions) new FirefoxSettings().settings();
                default -> throw new BrowserNotSupportedException(browserName);
            }

            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", false
            ));
            capabilities.merge(options);

            return new RemoteWebDriver(new URL(remoteUrl), capabilities);
        }
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
            case "firefox" -> new FirefoxDriver((FirefoxOptions) new FirefoxSettings().settings());
            default -> throw new BrowserNotSupportedException(browserName);
        };
    }
}
