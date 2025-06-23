package ru.arutyunyan.factory.settings;

import ru.arutyunyan.data.BrowserModeData;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;


public class FirefoxSettings implements IBrowserSettings {

    private final String mode = System.getProperty("mode").toUpperCase();

    public AbstractDriverOptions<?> settings() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        BrowserModeData modeData = BrowserModeData.valueOf(mode);

        return switch (modeData) {
            case HEADLESS -> firefoxOptions.addArguments("--headless");
            case FULLSCREEN -> firefoxOptions.addArguments("--start-maximized");
            case KIOSK -> firefoxOptions.addArguments("--kiosk");
        };
    }
}
