package ru.arutyunyan.factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import ru.arutyunyan.data.BrowserModeData;


public class ChromeSettings implements IBrowserSettings {

    private final String mode = System.getProperty("mode").toUpperCase();

    @Override
    public AbstractDriverOptions<?> settings() {
        ChromeOptions options = new ChromeOptions();
        BrowserModeData modeData = BrowserModeData.valueOf(mode);

//        String profilePath = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + UUID.randomUUID();
//        options.addArguments("--user-data-dir=" + profilePath);


//        switch (modeData) {
//            case HEADLESS:
//                options.addArguments("--headless=new");
//                options.addArguments("--window-size=1920,1080");
//                options.addArguments("--disable-gpu");
//                options.addArguments("--no-sandbox");
//                options.addArguments("--disable-dev-shm-usage");
//                return options;
//            case FULLSCREEN:
//                return options.addArguments("--start-maximized");
//            case KIOSK:
//                return options.addArguments("--kiosk");
//        }
//        throw new ModeNotSupportedException(mode);
//    }
        return switch (modeData) {
            case HEADLESS -> options.addArguments("--headless=new")
                    .addArguments("--window-size=1920,1080")
                    .addArguments("--disable-gpu")
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage");
            case FULLSCREEN -> options.addArguments("--start-maximized");
            case KIOSK -> options.addArguments("--kiosk");
        };
    }
}