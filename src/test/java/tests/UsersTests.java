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
    private WishList wishList;

    @Inject
    private User user;

    @Test
    @Tag("test")
    @DisplayName("Управление пользовательского подарка. Проверка поиска и просмотра подарка.")
    public void userPresentView() {
        clientOtusPage
                .open()
                .pageTitleShouldBeSame("Регистрация")
                .registration(user)
                .authorization(user);

        usersPage
                .deleteAllWishLists()
                .buttonDeleteWishListShouldNotBeDisplayed()
                .createNewWishList()
                .formCreateNewWishList(wishList)
                .clickButtonCreate()
                .viewWishList()
                .nameWishListShouldNotBeDisplayed(wishList);
    }

    @Test
    @Tag("test")
    @DisplayName("Управление пользователем списка желаний. Проверка добавление желания и удаление.")
    public void userPresentWishList() {
        clientOtusPage
                .open()
                .pageTitleShouldBeSame("Регистрация")
                .registration(user)
                .authorization(user);

        usersPage
                .createNewWishList()
                .formCreateNewWishList(wishList)
                .clickButtonCreate()
                .addNameWishListShouldBeSame(wishList)
                .addDescriptionWishLisShouldBeSame(wishList)
                .wishListShouldBeDelete();
    }
}