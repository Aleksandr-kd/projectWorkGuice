package ru.arutyunyan.factory.settings;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import ru.arutyunyan.data.BrowserModeData;


public class ChromeSettings implements IBrowserSettings {

    private final String mode = System.getProperty("mode").toUpperCase();

    @Override
    public AbstractDriverOptions<?> settings() {
        WebDriverManager.chromedriver().browserVersion("139.0.7258.0").setup();
        ChromeOptions options = new ChromeOptions();
        BrowserModeData modeData = BrowserModeData.valueOf(mode);

        return switch (modeData) {
            case HEADLESS -> options.addArguments("--headless=new")
                    .addArguments("--window-size=1920,1080")
                    .addArguments("--disable-gpu")
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage")
                    .addArguments("--ignore-certificate-errors")
                    .addArguments("--allow-insecure-localhost");
            case FULLSCREEN -> options.addArguments("--start-maximized")
                    .addArguments("--ignore-certificate-errors")
                    .addArguments("--allow-insecure-localhost");
            case KIOSK -> options.addArguments("--kiosk")
                    .addArguments("--ignore-certificate-errors")
                    .addArguments("--allow-insecure-localhost");
        };
    }
}