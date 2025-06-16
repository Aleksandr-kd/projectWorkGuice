package ru.arutyunyan.extension;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.factory.WebDriverFactory;
import ru.arutyunyan.modules.GuiceDtoModule;
import ru.arutyunyan.modules.GuicePageModule;
import ru.arutyunyan.utils.AllureScreenshotUtils;

import java.net.MalformedURLException;

//
//
//public class TestSetupExtension implements BeforeEachCallback, AfterEachCallback {
//
//    private final AllureScreenshotUtils screenshotUtils = new AllureScreenshotUtils();
//
//    private Injector injector;
//
//    private WebDriver driver;
//
//    @Override
//    public void beforeEach(ExtensionContext context) {
//        ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(context.getUniqueId());
//        driver = new WebDriverFactory().getDriver();
//        context.getStore(namespace).put("driver", driver);
//
//        User user = new User();
//
//
//        this.injector = Guice.createInjector(
//                new GuicePageModule(driver),
//                new GuiceDtoModule()
//        );
//        Object testInstance = context.getTestInstance()
//                .orElseThrow(() -> new IllegalStateException("No test instance"));
//
//
//        injector.injectMembers(testInstance);
//    }
//
//    @Override
//    public void afterEach(ExtensionContext context) {
//        ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(context.getUniqueId());
//        driver = context.getStore(namespace).get("driver", WebDriver.class);
//        if (context.getExecutionException().isPresent()) {
//            screenshotUtils.takeScreenshot(driver, "Error: " + context.getDisplayName());
//        }
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
//
public class TestSetupExtension implements BeforeEachCallback, AfterEachCallback {

    private final AllureScreenshotUtils screenshotUtils = new AllureScreenshotUtils();

    @Override
    public void beforeEach(ExtensionContext context) throws MalformedURLException {
        // Создаём уникальный namespace с учётом идентификатора потока
        ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(
                context.getUniqueId(),
                Thread.currentThread().getId()
        );

        // Создаём драйвер для текущего теста
        WebDriver driver = new WebDriverFactory().getDriver();
        context.getStore(namespace).put("driver", driver);

        User user = new User();

        // Создаём инжектор для текущего теста
        Injector injector = Guice.createInjector(
                new GuicePageModule(driver),
                new GuiceDtoModule()
        );

        Object testInstance = context.getTestInstance()
                .orElseThrow(() -> new IllegalStateException("No test instance"));

        injector.injectMembers(testInstance);

        // Сохраняем инжектор в store
        context.getStore(namespace).put("injector", injector);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(
                context.getUniqueId(),
                Thread.currentThread().getId()
        );

        WebDriver driver = context.getStore(namespace).get("driver", WebDriver.class);
        Injector injector = context.getStore(namespace).get("injector", Injector.class);

        try {
            if (context.getExecutionException().isPresent()) {
                screenshotUtils.takeScreenshot(driver, "Error: " + context.getDisplayName());
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
            // Очищаем ресурсы
            context.getStore(namespace).remove("driver");
            context.getStore(namespace).remove("injector");
        }
    }
}