package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.dto.WishList;
import ru.arutyunyan.extension.TestSetupExtension;
import ru.arutyunyan.factory.UserFactory;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import ru.arutyunyan.pages.otus.UsersPage;


@ExtendWith(TestSetupExtension.class)
public class UsersTest {

    @Inject
    private ClientOtusPage clientOtusPage;

    @Inject
    private UsersPage usersPage;

    @Inject
    private WishList wishList;

    @Test
    @Tag("test")
    @DisplayName("Управление пользователем списка желаний.")
    public void userPresentWishList() throws InterruptedException {
        User user = UserFactory.generateUser();

        usersPage.open();

        User registeredUser = clientOtusPage
                .pageTitleShouldBeSame("Регистрация")
                .registration(user);

        clientOtusPage.authorization(registeredUser);

        usersPage
                .clickCreateNewWishList()
                .formCreateNewWishList(wishList)
                .clickButtonCreate()
                .addNameWishListShouldBeSame(wishList)
                .addDescriptionWishLisShouldBeSame(wishList)
                .wishListShouldBeDelete();
    }
}