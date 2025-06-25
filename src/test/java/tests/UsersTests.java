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
public class UsersTests {

    @Inject
    private ClientOtusPage clientOtusPage;

    @Inject
    private UsersPage usersPage;

    @Inject
    private WishList wishList;

    @Test
    @Tag("test")
    @DisplayName("Управление пользовательского подарка. Поиск и удаление подарка.")
    public void userPresentView() throws InterruptedException {
        User user = UserFactory.generateUser();

        clientOtusPage
                .open()
                .pageTitleShouldBeSame("Регистрация");

        User registeredUser = clientOtusPage
                .registration(user);

        clientOtusPage
                .authorization(registeredUser);

        usersPage
                .deleteAllWishLists()
                .buttonDeleteWishListShouldNotBeDisplayed()
                .createNewWishList()
                .formCreateNewWishList(wishList)
                .clickButtonCreate()
                .viewWishList()
                .nameWishListShouldNotBeDisplayed(wishList);
    }
}