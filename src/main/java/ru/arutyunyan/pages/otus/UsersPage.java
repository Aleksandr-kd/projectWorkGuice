package ru.arutyunyan.pages.otus;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.dto.WishList;
import ru.arutyunyan.pages.AbsBasePage;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Path("/register")
public class UsersPage extends AbsBasePage<UsersPage> {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[text()='Создать новый список']")
    private WebElement buttonCreateNewWishList;

    @FindBy(xpath = "//button[text()='Создать']")
    private WebElement buttonCreateWishList;

    @FindBy(xpath = "//button[text()='Удалить']")
    public WebElement buttonDeleteWish;

    @FindBy(xpath = "//button[text()='Удалить']")
    public List<WebElement> buttonDeleteWishList;

    @FindBy(xpath = "(//button[@class='btn btn-primary'])[last()]")
    private WebElement buttonViewWishList;

    @FindBy(xpath = "//label[text()='Название']/following-sibling::input")
    private WebElement namePresent;

    @FindBy(xpath = "//label[text()='Описание (необязательно)']/following-sibling::textarea")
    private WebElement descriptionPresent;

    @FindBy(xpath = "//div[@class='mt-5 container']//h2")
    private WebElement nameWishList;

    @FindBy(xpath = "//div[@class='g-4 row row-cols-lg-3 row-cols-md-2 row-cols-1']/*[last()]" +
            "//div[@class='card-title h5']")
    private WebElement lastNamePresent;

    @FindBy(xpath = "//div[@class='g-4 row row-cols-lg-3 row-cols-md-2 row-cols-1']/*[last()]" +
            "//p[@class='card-text']")
    private WebElement lastDescriptionPresent;

    @Step("Получение описание последнего желания")
    public String getPageTextDescriptionPresent() {
        return lastDescriptionPresent.getText();
    }

    @Step("Нажать кнопку Создать новый список")
    public UsersPage clickCreateNewWishList() {
        waiters.waitAndClick(buttonCreateNewWishList);
        return this;
    }

    @Step("Нажать кнопку Создать")
    public UsersPage clickButtonCreate() {
        buttonCreateWishList.click();
        return this;
    }

    @Step("Заполнить название")
    public void setNameNewWishList(WishList wishList) {
        namePresent.click();
        namePresent.sendKeys(wishList.getProductName());
    }

    @Step("Заполнить описание")
    public void setDescriptionNewWishList(WishList wishList) {
        descriptionPresent.click();
        descriptionPresent.sendKeys(wishList.getDescription());
    }

    @Step("Заполнить форму создания нового списка желаний")
    public UsersPage formCreateNewWishList(WishList wishList) {
        setNameNewWishList(wishList);
        setDescriptionNewWishList(wishList);
        return this;
    }

    @Step("Удаление желания")
    public boolean isDeletePresent() {
        buttonDeleteWish.click();
        return true;
    }

    @Step("Получение название последнего желания")
    public String getPageTextNameRegistrationPresent() {
        actions.sendEnd(2000, 1000);
        waiters.waitForElementVisible(lastNamePresent);
        return lastNamePresent.getText();
    }

    @Step("Скрол и клик по элементу: {element}")
    public UsersPage viewWishList() {
        actions.sendEnd(2000, 1000);
        buttonViewWishList.click();
        return this;
    }

    @Step("Получение названия последнего желания")
    public String getNameWishList() {
        actions.pause(2000);
        waiters.waitForElementVisible(nameWishList);
        return nameWishList.getText();
    }

    @Step("Удаление всех элементов списка")
    public UsersPage deleteAllWishLists() {
        actions.pause(2000);
        List<WebElement> deleteButtons = buttonDeleteWishList;

        while (!deleteButtons.isEmpty()) {
            try {
                deleteButtons.get(0).click();

                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.stalenessOf(deleteButtons.get(0)));

                deleteButtons = buttonDeleteWishList;

            } catch (StaleElementReferenceException e) {
                deleteButtons = buttonDeleteWishList;
            } catch (TimeoutException e) {
                throw new RuntimeException("Элемент не был удален в течение 5 секунд");
            }
        }
        return this;
    }

    @Step("Проверка кнопки удаления на отображение")
    public boolean verifyDeleteButtonNotPresent() {
        boolean isPresent;
        try {
            isPresent = buttonDeleteWish.isDisplayed();
        } catch (NoSuchElementException e) {
            isPresent = false;
        }
        return isPresent;
    }

    @Step("Проверка добавление желания по названию")
    public UsersPage checkAddNameWishList(WishList wishList) {
        assertThat(getPageTextNameRegistrationPresent())
                .as("Элемент с названием %s не найден", wishList.getProductName())
                .isEqualTo(wishList.getProductName());
        return this;
    }

    @Step("Проверка добавление желания по описанию")
    public UsersPage checkAddDescriptionWishLis(WishList wishList) {
        assertThat(wishList.getDescription())
                .as("Элемент с описанием %s не найден", getPageTextDescriptionPresent())
                .isEqualTo(getPageTextDescriptionPresent());
        return this;
    }

    @Step("Проверка удаления желания")
    public void isCheckDeleteWishList() {
        assertThat(isDeletePresent()).as("Проверка удаления элемента").isTrue();
    }

    @Step("Проверка наличие кнопки удаления на подарке")
    public UsersPage checkButtonDeleteWishList() {
        assertThat(verifyDeleteButtonNotPresent())
                .withFailMessage("Не все подарки удалились")
                .isFalse();
        return this;
    }

    @Step("Проверка наличие подарка по названию")
    public void checkForNameWishList(WishList wishList) {
        assertThat(getNameWishList())
                .as("Элемент с названием %s не найден", wishList.getProductName())
                .isEqualTo(wishList.getProductName());
    }
}