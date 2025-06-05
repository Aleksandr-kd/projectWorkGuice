package ru.arutyunyan.pages.otus;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.pages.AbsBasePage;

import static org.assertj.core.api.Assertions.assertThat;


@Path("/register")
public class ClientOtusPage extends AbsBasePage<ClientOtusPage> {
    public ClientOtusPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[text()='Не удалось зарегистрировать пользователя']")
    private WebElement notRegistrationMessage;

    @FindBy(xpath = "//h2[text()='Вход в систему']")
    private WebElement getButtonLogin;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement buttonLogin;

    @FindBy(xpath = "//h2[text()='Регистрация']")
    private WebElement pageHeaderRegistration;

    @FindBy(xpath = "//input[@type='text']")
    private WebElement inputName;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement buttonRegistration;

    @FindBy(xpath = "//h2[text()='Мои списки желаний']")
    private WebElement listWishList;

    @Step("Получение текста авторизации")
    public String getTextAccount() {
        return waiters.waitAndGetText(listWishList);
    }

    @Step("Получение текста страницы Вход в систему")
    public String getTextLogin() {
        return waiters.waitAndGetText(getButtonLogin);
    }

    @Step("Получение текста страницы Регистрации")
    public String getPageTextRegistration() {
        return pageHeaderRegistration.getText();
    }

    @Step("Нажать кнопку зарегистрировать")
    public void clickButtonRegistration() {
        buttonRegistration.click();
    }

    @Step("Нажать кнопку Войти")
    public void clickButtonLogin() {
        buttonLogin.click();
    }

    @Step("Заполнение формы регистрации пользователя")
    public ClientOtusPage registration(User user) {
        int attempt = 1;

        while (attempt <= 5) {
            try {
                actions.pause(2000);

                inputName.clear();
                inputName.sendKeys(user.getName());
                actions.pause(1000);

                inputEmail.clear();
                inputEmail.sendKeys(user.getEmail());
                actions.pause(1000);

                inputPassword.clear();
                inputPassword.sendKeys(user.getPassword());
                actions.pause(1000);

                clickButtonRegistration();
                waiters.waitForPageLoad();

                if (!isErrorMessageDisplayed()) {
                    return this;
                }
            } catch (Exception e) {
                System.out.println("Попытка " + attempt + " не удалась: " + e.getMessage());
            }

            user = new User();
            attempt++;
            actions.pause(2000);
        }
        throw new RuntimeException("Не удалось зарегистрировать пользователя");
    }

    private boolean isErrorMessageDisplayed() {
        try {
            return notRegistrationMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Заполнение формы авторизации пользователя")
    public ClientOtusPage authorization(User user) {
        actions.pause(2000);

        inputName.clear();
        inputName.sendKeys(user.getName());
        actions.pause(1000);

        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());
        actions.pause(1000);

        clickButtonLogin();
        waiters.waitForPageLoad();
        return this;
    }

    @Step("Проверка, что заголовок страницы соответствует '{title}'")
    public ClientOtusPage pageTitleShouldBeSame(String title) {
        assertThat(getPageTextRegistration()).isEqualTo(title);
        return this;
    }

    @Step("Проверка, что заголовок страницы соответствует '{title}' для авторизации пользователя")
    public ClientOtusPage pageTitleRegistrationShouldBeSame(String title) {
        assertThat(getTextLogin()).isEqualTo(title);
        return this;
    }

    @Step("Проверка, что пользователь авторизован '{title}'")
    public void pageTitleAuthorizationShouldBeSame(String title) {
        assertThat(getTextAccount()).isEqualTo(title);
    }
}