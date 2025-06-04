package ru.arutyunyan.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import ru.arutyunyan.pages.otus.ClientOtusPage;
import ru.arutyunyan.pages.otus.UsersPage;
import ru.arutyunyan.pages.otus.WishListUsersPage;


public class GuicePageModule extends AbstractModule {

    private WebDriver driver;

    public GuicePageModule(WebDriver driver) {
        this.driver= driver;
    }

    @Provides
    @Singleton
    public ClientOtusPage clientOtusPage(){
        return new ClientOtusPage(driver);
    }

    @Provides
    @Singleton
    public UsersPage usersPage(){
        return new UsersPage(driver);
    }

    @Provides
    @Singleton
    public WishListUsersPage wishListUsersPage(){
        return new WishListUsersPage(driver);
    }
}
