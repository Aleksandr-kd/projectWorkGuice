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

    @Test
    @Tag("test")
    @DisplayName("Регистрации пользователя.")
    public void userRegistration() {
        User user = new User();

        clientOtusPage
                .open()
                .pageTitleShouldBeSame("Регистрация");

        User registeredUser = clientOtusPage
                .registration(user);

        clientOtusPage
                .pageTitleRegistrationShouldBeSame("Вход в систему")
                .authorization(registeredUser)
                .pageTitleAuthorizationShouldBeSame("Мои списки желаний");
    }
}

