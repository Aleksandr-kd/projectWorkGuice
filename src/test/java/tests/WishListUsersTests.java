package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.arutyunyan.extension.TestSetupExtension;
import ru.arutyunyan.pages.otus.WishListUsersPage;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestSetupExtension.class)
public class WishListUsersTests {

    @Inject
    ClientOtusPage clientOtusPage;

    @Inject
    private WishListUsersPage wishListUsersPage;

    @Inject
    private User user;

    @Test
    @Tag("users3")
    @DisplayName("Управление пользователем списка желаний.")
    public void userPresentWishList() {

        String titleUsers = "Пользователи";
        String titleWishListUsers = "Списки желаний пользователя";

        wishListUsersPage.open();
        clientOtusPage.registration(user);
        clientOtusPage.authorization(user);
        wishListUsersPage.openUsers();

        String textUsers = wishListUsersPage.getPageTextUsers();
        assertThat(textUsers).isEqualTo(titleUsers);

        wishListUsersPage.viewWistListOne();

        String getTextPageTextWishListUsersOne = wishListUsersPage.getTextPageTextWishListUsers();
        assertThat(getTextPageTextWishListUsersOne).isEqualTo(titleWishListUsers);
        wishListUsersPage.backList();

        wishListUsersPage.viewWistListLast();
        String getTextPageTextWishListUsersLast = wishListUsersPage.getTextPageTextWishListUsers();
        assertThat(getTextPageTextWishListUsersLast).isEqualTo(titleWishListUsers);

        wishListUsersPage.backList();
    }
}