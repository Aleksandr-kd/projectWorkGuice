package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.extension.TestSetupExtension;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import ru.arutyunyan.pages.otus.WishListUsersPage;


@ExtendWith(TestSetupExtension.class)
public class WishListUsersTests {

    @Inject
    private ClientOtusPage clientOtusPage;

    @Inject
    private WishListUsersPage wishListUsersPage;

    @Inject
    private User user;

    @Test
    @Tag("test")
    @DisplayName("Просмотр желаний всех пользователей")
    public void userPresentWishList() {
        clientOtusPage
                .open()
                .registration(user)
                .authorization(user);

        wishListUsersPage
                .openUsers()
                .pageTitleShouldBeSame("Пользователи")
                .viewWistListFirst()
                .pageTitleListShouldBeSame("Списки желаний пользователя")
                .backListUsers()
                .viewWistListLast()
                .pageNameShouldBeSame("Списки желаний пользователя")
                .backListUsers();
    }
}