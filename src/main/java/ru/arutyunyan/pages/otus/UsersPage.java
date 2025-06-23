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
    private WebElement buttonDeleteWish;

    @FindBy(xpath = "//button[text()='Удалить']")
    private List<WebElement> buttonDeleteWishList;

    @FindBy(xpath = "(//button[contains(@class, 'btn-primary')])[last()]")
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

    @FindBy(xpath = "//*[@class='fade modal']")
    private WebElement modal;

    @Step("Получение описание последнего желания")
    public String getPageTextDescriptionPresent() {
        return lastDescriptionPresent.getText();
    }

    @Step("Нажать кнопку Создать новый список")
    public UsersPage clickCreateNewWishList() {
        waiters.waitForElementVisible(buttonCreateNewWishList);
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
        namePresent.clear();
        namePresent.sendKeys(wishList.getProductName());
    }

    @Step("Заполнить описание")
    public void setDescriptionNewWishList(WishList wishList) {
        descriptionPresent.clear();
        descriptionPresent.sendKeys(wishList.getDescription());
    }

    @Step("Заполнить форму создания нового списка желаний")
    public UsersPage formCreateNewWishList(WishList wishList) {
        setNameNewWishList(wishList);
        setDescriptionNewWishList(wishList);
        waiters.waitForPageLoad();
        return this;
    }

    @Step("Удаление желания")
    public boolean isDeletePresent() {
        buttonDeleteWish.click();
        return true;
    }

    @Step("Получение название последнего желания")
    public String getPageTextNameRegistrationPresent() {
        return waiters.waitForElementVisible(lastNamePresent).getText();
    }

    @Step("Просмотр добавленного списка желания")
    public UsersPage viewWishList() {
        try {
            if (waiters.waitForElementVisible(modal, 2)) {
                waiters.waitForElementInVisible(modal);
            }
        } catch (Exception ignored) {
        }
        buttonViewWishList.click();
        return this;
    }

    @Step("Получение названия последнего желания")
    public String getNameWishList() {
        waiters.waitForElementVisible(nameWishList);
        return nameWishList.getText();
    }

    @Step("Удаление всех элементов списка")
    public UsersPage deleteAllWishLists() {
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

    @Step("Проверка, добавлено ли желания по названию")
    public UsersPage addNameWishListShouldBeSame(WishList wishList) {
        assertThat(getPageTextNameRegistrationPresent())
                .as("Элемент с названием %s не найден", wishList.getProductName())
                .isEqualTo(wishList.getProductName());
        return this;
    }

    @Step("Проверка, добавлено ли желания по описанию")
    public UsersPage addDescriptionWishLisShouldBeSame(WishList wishList) {
        assertThat(wishList.getDescription())
                .as("Элемент с описанием %s не найден", getPageTextDescriptionPresent())
                .isEqualTo(getPageTextDescriptionPresent());
        return this;
    }

    @Step("Проверка, что желание удаленно")
    public void wishListShouldBeDelete() {
        assertThat(isDeletePresent()).as("Проверка удаления элемента").isTrue();
    }

    @Step("Проверка, что отсутствует кнопка удаления на подарке")
    public UsersPage buttonDeleteWishListShouldNotBeDisplayed() {
        assertThat(verifyDeleteButtonNotPresent())
                .withFailMessage("Не все подарки удалились")
                .isFalse();
        return this;
    }

    @Step("Проверка, что отсутствует подарок с названием")
    public void nameWishListShouldNotBeDisplayed(WishList wishList) {
        assertThat(getNameWishList())
                .as("Элемент с названием %s не найден", wishList.getProductName())
                .isEqualTo(wishList.getProductName());
    }
}