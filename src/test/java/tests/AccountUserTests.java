package tests;

import jakarta.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import org.junit.jupiter.api.*;
import ru.arutyunyan.extension.TestSetupExtension;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(TestSetupExtension.class)
public class AccountUserTests {

    @Inject
    private ClientOtusPage clientOtusPage;

    @Inject
    private User user;

    @Test
    @Tag("users1")
    @DisplayName("Регистрации пользователя.")
    public void userRegistration() {
        String titleLogin = "Вход в систему";
        String titleRegistration = "Регистрация";
        String titleAccount = "Мои списки желаний";

        clientOtusPage.open();

        String textRegistration = clientOtusPage.getPageTextRegistration();
        assertThat(textRegistration).isEqualTo(titleRegistration);

        clientOtusPage.registration(user);

        String textLogin = clientOtusPage.getTextLogin();
        assertThat(textLogin).isEqualTo(titleLogin);

        clientOtusPage.authorization(user);
        String textAccount = clientOtusPage.getTextAccount();
        assertThat(textAccount).isEqualTo(titleAccount);
    }
}

