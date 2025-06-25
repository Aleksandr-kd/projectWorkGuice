package ru.arutyunyan.pages.otus;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.factory.UserFactory;
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
        waiters.waitForElementVisible(buttonRegistration);
        waiters.waitAndClick(buttonRegistration);
    }

    @Step("Нажать кнопку Войти")
    public void clickButtonLogin() {
        buttonLogin.isDisplayed();
        buttonLogin.click();
    }

    @Step("Заполнение формы регистрации пользователя")
    public User registration(User baseUser) {
        int attempt = 1;
        String suffix = "";
        User currentUser = baseUser;

        while (attempt <= 5) {
            try {
                if (attempt > 1) {
                    suffix += getRandomChar();
                    currentUser = UserFactory.mutateUser(baseUser, suffix);
                    Allure.step("Попытка #" + attempt + ": " + currentUser);
                }

                fillForm(currentUser);

                Allure.step("Регистрируем: " + currentUser.getName() + ", " + currentUser.getPassword());
                if (!isErrorPresent()) {
                    return currentUser;
                }
            } catch (Exception e) {
                Allure.step("Ошибка: " + e.getMessage());
            }
            attempt++;
        }

        throw new RuntimeException("Не удалось зарегистрировать пользователя");
    }
//    @Step("Заполнение формы регистрации пользователя")
//    public ClientOtusPage registration(User user) {
//        int attempt = 1;
//        String suffix = "";
//
//        while (attempt <= 5) {
//            try {
//                if (attempt > 1) {
//                    suffix += getRandomChar();
////                    newUser(user, suffix);
//                    Allure.step("Попытка #" + attempt + ": " + user.getEmail());
//                }
//                fillForm(user);
//
//                Allure.step("Регистрируем: " + user.getName() + ", " + user.getPassword());
//
//                if (!isErrorPresent()) {
//                    return this;
//                }
//            } catch (Exception e) {
//                Allure.step("Ошибка: " + e.getMessage());
//            }
//            attempt++;
//        }
//        throw new RuntimeException("Не удалось зарегистрировать пользователя");
//    }

//    @Step("Получаем нового пользователя")
//    private void newUser(User user, String suffix) {
//
//        String oldName = user.getName();
//        String oldEmail = user.getEmail();
//        String oldPassword = user.getPassword();
//
//        user.setName(oldName + suffix);
//        user.setEmail(oldEmail.replace("@", suffix + "@"));
//        user.setPassword(oldPassword + suffix);
//
//        Allure.step("Изменение юзера: "
//                + "Было: " + oldName + ", стало: " + user.getName()
//                + "\nБыло: " + oldEmail + ", стало: " + user.getEmail()
//                + "\nПароль был: " + oldPassword + ", стал: " + user.getPassword());
//    }

    private void fillForm(User user) {
        Allure.step("Вводим данныe: "
                + "\nname: " + user.getName()
                + "\nemail: " + user.getEmail()
                + "\npassword = " + user.getPassword());

        waiters.waitForPageLoad();
        waiters.waitAndClick(inputName);
        inputName.clear();
        inputName.sendKeys(user.getName());

        try {
            if (inputEmail != null && inputEmail.isDisplayed()) {

                waiters.waitAndClick(inputEmail);
                inputEmail.clear();
                inputEmail.sendKeys(user.getEmail());
            }
        } catch (Exception e) {
            Allure.step("Email поле не найдено");
        }

        waiters.waitAndClick(inputPassword);
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

    @Step("Заполнение формы авторизации пользователя")
    public ClientOtusPage authorization(User user) {
        waiters.waitForElementVisible(inputName);
        Allure.step("Ввод логина: " + user.getName());

        inputName.clear();
        inputName.sendKeys(user.getName());

        waiters.waitForElementVisible(inputPassword);
        Allure.step("Ввод пароля: " + user.getPassword());

        inputPassword.clear();
        inputPassword.sendKeys(user.getPassword());

        clickButtonLogin();

        Allure.step("Авторизация. Ожидаем логин: " + user.getName());
        Allure.step("Авторизация. Ожидаем пароль: " + user.getPassword());

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