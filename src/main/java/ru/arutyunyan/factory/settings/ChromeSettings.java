package ru.arutyunyan.factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import ru.arutyunyan.data.BrowserModeData;
import ru.arutyunyan.exceptions.ModeNotSupportedException;

import java.util.UUID;


public class ChromeSettings implements IBrowserSettings {

    private final String mode = System.getProperty("mode").toUpperCase();

    @Override
    public AbstractDriverOptions<?> settings() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");

        String profilePath = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + UUID.randomUUID();
        options.addArguments("--user-data-dir=" + profilePath);

        BrowserModeData modeData = BrowserModeData.valueOf(mode);

        switch (modeData) {
            case HEADLESS:
                options.addArguments("--headless=new");
                return options;
            case FULLSCREEN:
                return options.addArguments("--start-maximized");
            case KIOSK:
                return options.addArguments("--kiosk");
        }
        throw new ModeNotSupportedException(mode);
    }
}
