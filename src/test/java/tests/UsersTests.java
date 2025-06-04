package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.dto.WishList;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.arutyunyan.extension.TestSetupExtension;
import ru.arutyunyan.pages.otus.UsersPage;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestSetupExtension.class)
public class UsersTests {

    @Inject
    ClientOtusPage clientOtusPage;

    @Inject
    private UsersPage usersPage;

    @Inject
    private User user;

    @Inject
    WishList wishList;

    @Test
    @Tag("users2")
    @DisplayName("Управление пользователем списка желаний.")
    public void userPresentWishList() {
        String nameProduct = wishList.getProductName();
        String description = wishList.getDescription();

        usersPage.open();
        clientOtusPage.registration(user);
        clientOtusPage.authorization(user);
        usersPage.clickCreateNewWishList();
        usersPage.formCreateNewWishList(wishList);
        usersPage.clickButtonCreate();

        String nameCheck = usersPage.getPageTextNameRegistrationPresent();
        assertThat(nameCheck)
                .as("Элемент с названием %s не найден", nameProduct)
                .isEqualTo(nameProduct);

        String descriptionCheck = usersPage.getPageTextDescriptionPresent();
        assertThat(description)
                .as("Элемент с описанием %s не найден", descriptionCheck)
                .isEqualTo(descriptionCheck);

        Boolean isDeletePresent = usersPage.isDeletePresent();
        assertThat(isDeletePresent).as("Проверка удаления элемента").isTrue();
    }

    @Test
    @Tag("users2")
    @DisplayName("Управление пользовательского подарка. Поиск и удаление подарка.")
    public void userPresentView() {

        String nameProduct = wishList.getProductName();
        String description = wishList.getDescription();

        usersPage.open();
        clientOtusPage.registration(user);
        clientOtusPage.authorization(user);
        usersPage.deleteAllWishLists();

        assertThat(usersPage.verifyDeleteButtonNotPresent())
                .withFailMessage("Не все подарки удалились")
                .isFalse();

        usersPage.clickCreateNewWishList();
        usersPage.formCreateNewWishList(wishList);
        usersPage.clickButtonCreate();
        usersPage.viewWishList();

        String nameCheck = usersPage.getNameWishList();
        assertThat(nameCheck)
                .as("Элемент с названием %s не найден", nameProduct)
                .isEqualTo(nameProduct);


        usersPage.isDeleteWishList();

        Boolean isDeletePresent = usersPage.isDeleteWishList();
        assertThat(isDeletePresent).as("Проверка удаления элемента").isTrue();

    }
}