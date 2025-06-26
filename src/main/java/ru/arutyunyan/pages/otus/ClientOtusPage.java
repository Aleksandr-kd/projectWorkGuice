package ru.arutyunyan.pages.otus;

import io.qameta.allure.Allure;
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

    @FindBy(xpath = "//div[text()='Не удалось зарегистрировать пользователя']")
    private WebElement notRegistrationMessage;

    @FindBy(xpath = "//h2[text()='Вход в систему']")
    private WebElement textButtonLogin;

    @FindBy(xpath = "//button[@type='submit']")
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

    @FindBy(xpath = "//*[@class='fade modal']")
    private WebElement modal;

    @Step("Получение текста авторизации")
    public String getTextAccount() {
        return waiters.waitAndGetText(listWishList);
    }

    @Step("Получение текста страницы Вход в систему")
    public String getTextLogin() {
        return waiters.waitAndGetText(textButtonLogin);
    }

    @Step("Получение текста страницы Регистрации")
    public String getPageTextRegistration() {
        return pageHeaderRegistration.getText();
    }

    @Step("Нажать кнопку зарегистрировать")
    public void clickButtonRegistration() {
        waiters.waitAndClick(buttonRegistration);
    }

    @Step("Нажать кнопку Войти")
    public void clickButtonLogin() {
        buttonLogin.click();
    }

    public ClientOtusPage registration(User user) {
        Allure.step("Вводим данныe: "
                + "\nname: " + user.getName()
                + "\nemail: " + user.getEmail()
                + "\npassword = " + user.getPassword());

        waiters.waitForElementVisible(pageHeaderRegistration);
        inputName.clear();
        inputName.sendKeys(user.getName());

        inputEmail.clear();
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());

        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

        clickButtonRegistration();
        return this;
    }

    @Step("Заполнение формы авторизации пользователя")
    public ClientOtusPage authorization(User user) {
        Allure.step("Ввод логина: " + user.getName());
        waiters.waitForElementVisible(textButtonLogin);
        inputName.clear();
        inputName.sendKeys(user.getName());

        Allure.step("Ввод пароля: " + user.getPassword());
        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

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