package ru.arutyunyan.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ru.arutyunyan.dto.User;
import ru.arutyunyan.dto.WishList;
import ru.arutyunyan.testdata.factories.UserFactory;


public class GuiceDtoModule extends AbstractModule {

    @Provides
    public WishList getWishlist() {
        return new WishList();
    }

    @Provides
    public User getUser(UserFactory factory) {
        return factory.generate();
    }
}

