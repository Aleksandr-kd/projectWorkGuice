package ru.arutyunyan.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.dto.WishList;


public class GuiceDtoModule extends AbstractModule {

    @Provides
    public User getUser() {
        return new User();
    }

    @Provides
    public WishList getWishlist() {
        return new WishList();
    }
}

