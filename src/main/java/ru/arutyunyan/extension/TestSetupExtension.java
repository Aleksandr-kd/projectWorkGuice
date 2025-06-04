package ru.arutyunyan.extension;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import ru.arutyunyan.factory.WebDriverFactory;
import ru.arutyunyan.modules.GuiceDtoModule;
import ru.arutyunyan.modules.GuicePageModule;
import ru.arutyunyan.utils.AllureScreenshotUtils;


public class TestSetupExtension implements BeforeEachCallback, AfterEachCallback {

    private final AllureScreenshotUtils screenshotUtils = new AllureScreenshotUtils();

    private Injector injector;

    private WebDriver driver;

    private final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(TestSetupExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        driver = new WebDriverFactory().getDriver();
        context.getStore(namespace).put("driver", driver);

        this.injector = Guice.createInjector(
                new GuicePageModule(driver),
                new GuiceDtoModule()
        );
        Object testInstance = context.getTestInstance()
                .orElseThrow(() -> new IllegalStateException("No test instance"));
        injector.injectMembers(testInstance);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        driver = context.getStore(namespace).get("driver", WebDriver.class);
        if (context.getExecutionException().isPresent()) {
            screenshotUtils.takeScreenshot(driver, "Error: " + context.getDisplayName());
        }
        if (driver != null) {
            driver.quit();
        }
    }
}

