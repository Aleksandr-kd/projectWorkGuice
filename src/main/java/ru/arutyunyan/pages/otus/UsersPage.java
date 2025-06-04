package ru.arutyunyan.pages.otus;

import io.qameta.allure.Step;
import ru.arutyunyan.annotations.Path;
import ru.arutyunyan.dto.WishList;
import ru.arutyunyan.pages.AbsBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;


@Path("/register")
public class UsersPage extends AbsBasePage<UsersPage> {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

//    @FindBy(xpath = "//button[text()='Войти']")
//    private WebElement buttonLogin;
//
//    @FindBy(xpath = "//input[@type='text']")
//    private WebElement inputName;
//
//    @FindBy(xpath = "//input[@type='password']")
//    private WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Создать новый список']")
    private WebElement buttonCreateNewWishList;

    @FindBy(xpath = "//button[text()='Создать']")
    private WebElement buttonCreateWishList;

    @FindBy(xpath = "//button[text()='Удалить']")
    public WebElement buttonDeleteWish;

    @FindBy(xpath = "//button[text()='Удалить']")
    public List<WebElement> buttonDeleteWishList;

//    @FindBy(xpath = "(//div[@class='col'])[last()]")
//    private WebElement lastCard;


    @FindBy(xpath = "(//button[@class='btn btn-primary'])[last()]")
    private WebElement buttonViewWishList;


    @FindBy(xpath = "//button[text()='Добавить подарок']")
    private WebElement buttonAddPresent;

    @FindBy(xpath = "//button[text()='Удалить список']")
    private WebElement buttonDeletePresent;

    @FindBy(xpath = "//label[text()='Название']/following-sibling::input")
    private WebElement namePresent;

    @FindBy(xpath = "//label[text()='Описание (необязательно)']/following-sibling::textarea")
    private WebElement descriptionPresent;

    @FindBy(xpath = "//label[text()='Ссылка на магазин (необязательно)']/following-sibling::input")
    private WebElement linkStorePresent;

    @FindBy(xpath = "//label[text()='Цена (необязательно)']/following-sibling::input")
    private WebElement pricePresent;

    @FindBy(xpath = "//label[text()='Ссылка на изображение (необязательно)']/following-sibling::input")
    private WebElement linkImagePresent;

    @FindBy(xpath = "//button[text()='Добавить']")
    private WebElement buttonAddForm;

    @FindBy(xpath = "//div[@class='mt-5 container']//h2")
    private WebElement nameWishList;

    @FindBy(xpath = "//div[@class='g-4 row row-cols-lg-3 row-cols-md-2 row-cols-1']/*[last()]" +
            "//div[@class='card-title h5']")
    private WebElement lastNamePresent;

    @FindBy(xpath = "//div[@class='g-4 row row-cols-lg-3 row-cols-md-2 row-cols-1']/*[last()]" +
            "//p[@class='card-text']")
    private WebElement lastDescriptionPresent;

    @FindBy(xpath = "//input[@type='email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement buttonRegistration;

    @Step("Получение описание последнего желания")
    public String getPageTextDescriptionPresent() {
        return lastDescriptionPresent.getText();
    }

    @Step("Нажать кнопку Создать новый список")
    public void clickCreateNewWishList() {
        waiters.waitAndClick(buttonCreateNewWishList);
    }

    @Step("Нажать кнопку Создать")
    public void clickButtonCreate() {
        buttonCreateWishList.click();
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
    public void formCreateNewWishList(WishList wishList) {
        setNameNewWishList(wishList);
        setDescriptionNewWishList(wishList);
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

    @Step("Скролл и клик по элементу: {element}")
    public void viewWishList() {
        actions.sendEnd(2000, 1000);
        buttonViewWishList.click();
    }

    @Step("Получение названия последнего желания")
    public String getNameWishList() {
        actions.pause(2000);
        waiters.waitForElementVisible(nameWishList);
        return nameWishList.getText();
    }

    @Step("Удаление списка")
    public boolean isDeleteWishList() {
        buttonDeletePresent.click();
        return true;
    }

    @Step("Удаление всех элементов списка")
    public void deleteAllWishLists() {
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
}