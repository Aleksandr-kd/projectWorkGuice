package ru.arutyunyan.pages.otus;

import io.qameta.allure.Step;
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
        inputName.clear();
        inputName.sendKeys(user.getName());

        inputEmail.clear();
        inputEmail.sendKeys(user.getEmail());

        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

        clickButtonRegistration();
        waiters.waitForPageLoad();
        return this;
    }

    @Step("Заполнение формы авторизации пользователя")
    public ClientOtusPage authorization(User user) {
        actions.pause(2000);

        inputName.clear();
        inputName.sendKeys(user.getName());

        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

        clickButtonLogin();
        waiters.waitForPageLoad();
        return this;
    }

    @Step("Проверка открытия нужной страницы")
    public ClientOtusPage pageTitleShouldBeSames(String title) {
        assertThat(getPageTextRegistration()).isEqualTo(title);
        return this;
    }

    @Step("Проверка открытия страницы зарегистрированного пользователя")
    public ClientOtusPage pageTitleRegistrationShouldBeSames(String title) {
        assertThat(getTextLogin()).isEqualTo(title);
        return this;
    }

    @Step("Проверка авторизации пользователя")
    public void checkAuthorization(String title) {
        assertThat(getTextAccount()).isEqualTo(title);
    }
}