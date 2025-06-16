package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.extension.TestSetupExtension;
import ru.arutyunyan.pages.otus.ClientOtusPage;


@ExtendWith(TestSetupExtension.class)
public class AccountUserTests {

    @Inject
    private ClientOtusPage clientOtusPage;

    @Inject
    private User user;

    @Test
    @Tag("test")
    @DisplayName("Регистрации пользователя.")
    public void userRegistration() {

        clientOtusPage
                .open()
                .pageTitleShouldBeSame("Регистрация")
                .registration(user)
                .pageTitleRegistrationShouldBeSame("Вход в систему")
                .authorization(user)
                .pageTitleAuthorizationShouldBeSame("Мои списки желаний");
    }
}

