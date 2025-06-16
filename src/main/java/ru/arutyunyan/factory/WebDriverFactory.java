package ru.arutyunyan.factory;

import org.openqa.selenium.MutableCapabilities;
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
    private final String browserVersion = System.getProperty("browserVersion");
    private String remoteUrl = System.getProperty("remote.url", "");

    public WebDriver getDriver() throws MalformedURLException {
        if (!remoteUrl.isEmpty()) {
            MutableCapabilities mutableCapabilities = new MutableCapabilities();
            mutableCapabilities.setCapability("browserName", browserName);
            mutableCapabilities.setCapability("browserVersion", browserVersion);
            return new RemoteWebDriver(new URL(remoteUrl), mutableCapabilities);
        }
        switch (browserName.toLowerCase()) {
            case "chrome":
                return new ChromeDriver((ChromeOptions) new ChromeSettings().settings());
            case "firefox":
                return new FirefoxDriver((FirefoxOptions) new FirefoxSettings().settings());
            default:
                throw new BrowserNotSupportedException(browserName);
        }
    }
}

