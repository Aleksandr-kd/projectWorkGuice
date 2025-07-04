package ru.arutyunyan.factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import ru.arutyunyan.data.BrowserModeData;
import ru.arutyunyan.exceptions.ModeNotSupportedException;


public class ChromeSettings implements IBrowserSettings {

    private final String mode = System.getProperty("mode").toUpperCase();

    @Override
    public AbstractDriverOptions<?> settings() {
        ChromeOptions chromeOptions = new ChromeOptions();
        BrowserModeData modeData = BrowserModeData.valueOf(mode);

        switch (modeData) {
            case HEADLESS:
                chromeOptions.addArguments("--headless=new").addArguments("--window-size=1920,1080");
                return chromeOptions;
            case FULLSCREEN:
                return chromeOptions.addArguments("--start-fullscreen");
            case KIOSK:
                return chromeOptions.addArguments("--kiosk");
        }
        throw new ModeNotSupportedException(mode);
    }
}