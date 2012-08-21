package com.alexshabanov.gus;

import com.alexshabanov.gus.service.AccountService;
import com.alexshabanov.gus.service.UserService;
import com.alexshabanov.gus.service.support.DefaultAccountService;
import com.alexshabanov.gus.service.support.DefaultUserService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public final class App implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Inject
    private UserService userService;

    @Override
    public void run() {
        LOGGER.info("Checking injected resources");

        LOGGER.info("Register new user, id = {}", userService.registerNewUser());
    }

    // app configuration
    public static final class AppModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(App.class);
            bind(AccountService.class).to(DefaultAccountService.class);
            bind(UserService.class).to(DefaultUserService.class);
        }
    }


    public static void main(String[] args) {
        LOGGER.info("Guice-only AOP");

        try {
            final Injector injector = Guice.createInjector(new AppModule());
            final Runnable runnableApp = injector.getInstance(App.class);

            runnableApp.run();
        } finally {
            LOGGER.info("END");
        }
    }
}
