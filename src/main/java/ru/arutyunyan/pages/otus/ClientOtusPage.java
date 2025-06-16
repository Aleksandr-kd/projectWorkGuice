package ru.arutyunyan.pages.otus;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.pages.AbsBasePage;

import java.util.Random;

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
        String suffix = "";

        while (attempt <= 5) {
            try {
                if (attempt > 1) {
                    suffix += getRandomChar();
                    newUser(user, suffix);
                    Allure.step("Попытка #" + attempt + ": " + user.getEmail());
                }
                fillForm(user);

                if (!isErrorPresent()) {
                    return this;
                }
            } catch (Exception e) {
                Allure.step("Ошибка: " + e.getMessage());
            }
            attempt++;
        }
        throw new RuntimeException("Не удалось зарегистрировать пользователя");
    }

    private void fillForm(User user) {
        Allure.step("Вводим данныe: "
                + "\nname: " + user.getName()
                + "\nemail: " + user.getEmail()
                + "\npassword = " + user.getPassword());

        inputName.clear();
        inputName.sendKeys(user.getName());

        inputEmail.clear();
        inputEmail.sendKeys(user.getEmail());

        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

        clickButtonRegistration();
        waiters.waitForPageLoad();
    }

    /**
     * Проверяет видимость сообщения об ошибке регистрации.
     *
     * @return true если сообщение отобразится в течение 2 секунд
     */
    private boolean isErrorPresent() {
        try {
            waiters.waitForElementVisible(notRegistrationMessage, 2);
            return notRegistrationMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Генерирует случайную строчную букву английского алфавита.
     *
     * @return Случайный символ из диапазона 'a'-'z'
     */
    private String getRandomChar() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        int index = new Random().nextInt(letters.length());
        return letters.substring(index, index + 1);
    }

    private void newUser(User user, String suffix) {
        String oldName = user.getName();
        String oldEmail = user.getEmail();

        user.setName(oldName + suffix);
        user.setEmail(oldEmail.replace("@", suffix + "@"));

        Allure.step("Изменение юзера: "
                + "Было: " + oldName + ", стало: " + user.getName()
                + "\nБыло: " + oldEmail + ", стало: " + user.getEmail());
    }

    @Step("Заполнение формы авторизации пользователя")
    public ClientOtusPage authorization(User user) {
        inputName.clear();
        inputName.sendKeys(user.getName());

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