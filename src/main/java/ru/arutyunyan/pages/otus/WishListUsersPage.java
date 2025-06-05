package ru.arutyunyan.pages.otus;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.pages.AbsBasePage;

import static org.assertj.core.api.Assertions.assertThat;


@Path("/register")
public class WishListUsersPage extends AbsBasePage<WishListUsersPage> {

    public WishListUsersPage(WebDriver driver) {
        super(driver);
    }

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

    @Step("Получение название страницы")
    public String getTextPageTextWishListUsers() {
        return pageTextWishListUsers.getText();
    }

    @Step("Открываю страницу пользователей")
    public WishListUsersPage openUsers() {
        actions.pause(2000);
        clickUsers.click();
        return this;
    }

    @Step("Просмотреть список желаний первого пользователя")
    public WishListUsersPage viewWistListOne() {
        actions.pause(2000);
        userOne.click();
        return this;
    }

    @Step("Просмотреть список желаний последнего пользователя")
    public WishListUsersPage viewWistListLast() {
        actions.sendEnd(1000, 1000);
        userLast.click();
        return this;
    }

    @Step("Вернутся назад к списку пользователей")
    public WishListUsersPage backListUsers() {
        actions.pause(2000);
        backList.click();
        return this;
    }

    @Step("Получение названия страницы")
    public String getPageTextUsers() {
        actions.pause(2000);
        return pageUsers.getText();
    }

    @Step("Проверка, что заголовок страницы соответствует '{title}'")
    public WishListUsersPage pageTitleShouldBeSame(String title) {
        assertThat(getPageTextUsers()).isEqualTo(title);
        return this;
    }

    @Step("Проверка, что заголовок страницы соответствует заголовку списками желаний '{title}'")
    public WishListUsersPage pageTitleListShouldBeSame(String title) {
        assertThat(getTextPageTextWishListUsers()).isEqualTo(title);
        return this;
    }

    @Step("Проверка, что заголовок страницы соответствует '{title}'")
    public WishListUsersPage pageNameShouldBeSame(String title) {
        assertThat(getTextPageTextWishListUsers()).isEqualTo(title);
        return this;
    }
}

