package ru.arutyunyan.pages.otus;

import io.qameta.allure.Step;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.pages.AbsBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Path("/register")
public class WishListUsersPage extends AbsBasePage<WishListUsersPage> {

    public WishListUsersPage(WebDriver driver) {
        super(driver);
    }
//
//    @FindBy(xpath = "//button[text()='Войти']")
//    private WebElement buttonLogin;
//
//    @FindBy(xpath = "//input[@type='text']")
//    private WebElement inputName;
//
//    @FindBy(xpath = "//input[@type='password']")
//    private WebElement inputPassword;

    @FindBy(xpath = "//a[text()='Пользователи']")
    private WebElement clickUsers;

    @FindBy(xpath = "//h2[@class='mb-4']")
    private WebElement pageUsers;

    @FindBy(xpath = "//a[@class='btn btn-primary']")
    private WebElement userOne;

    @FindBy(xpath = "(//a[@class='btn btn-primary'])[last()]")
    private WebElement userLast;

    @FindBy(xpath = "//a[@class='btn btn-secondary']/.")
    private WebElement backList;

    @FindBy(xpath = "//h2[text()='Списки желаний пользователя']")
    private WebElement pageTextWishListUsers;
//
//    @FindBy(xpath = "//input[@type='email']")
//    private WebElement inputEmail;
//
//    @FindBy(xpath = "//button[@type='submit']")
//    private WebElement buttonRegistration;

    @Step("Получение название страницы")
    public String getTextPageTextWishListUsers() {
        return pageTextWishListUsers.getText();
    }

    @Step("Открываю страницу пользователей")
    public void openUsers() {
        actions.pause(2000);
        clickUsers.click();
    }

    @Step("Просмотреть список желаний первого пользователя")
    public void viewWistListOne() {
        actions.pause(2000);
        userOne.click();
    }

    @Step("Просмотреть список желаний последнего пользователя")
    public void viewWistListLast() {
        actions.sendEnd(1000, 1000);
        userLast.click();
    }

    @Step("Вернутся назад к списку пользователей")
    public void backList() {
        actions.pause(2000);
        backList.click();
    }

    @Step("Получение названия страницы")
    public String getPageTextUsers() {
        actions.pause(2000);
        return pageUsers.getText();
    }
}

