package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.dto.WishList;
import ru.arutyunyan.extension.TestSetupExtension;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import ru.arutyunyan.pages.otus.UsersPage;


@ExtendWith(TestSetupExtension.class)
public class UsersTests {

    @Inject
    private ClientOtusPage clientOtusPage;

    @Inject
    private UsersPage usersPage;

    @Inject
    private User user;

    @Inject
    private WishList wishList;

    @Test
    @Tag("users")
    @DisplayName("Управление пользователем списка желаний.")
    public void userPresentWishList() {

        usersPage
                .open();

        clientOtusPage
                .registration(user)
                .authorization(user);

        usersPage
                .clickCreateNewWishList()
                .formCreateNewWishList(wishList)
                .clickButtonCreate()
                .checkAddNameWishList(wishList)
                .checkAddDescriptionWishLis(wishList)
                .isCheckDeleteWishList();

    }

    @Test
    @Tag("users")
    @DisplayName("Управление пользовательского подарка. Поиск и удаление подарка.")
    public void userPresentView() {

        usersPage
                .open();

        clientOtusPage
                .registration(user)
                .authorization(user);

        usersPage
                .deleteAllWishLists()
                .checkButtonDeleteWishList()
                .clickCreateNewWishList()
                .formCreateNewWishList(wishList)
                .clickButtonCreate()
                .viewWishList()
                .checkForNameWishList(wishList);
    }
}