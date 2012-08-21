package com.alexshabanov.gus.service.support;

import com.alexshabanov.gus.domain.User;
import com.alexshabanov.gus.service.AccountService;
import com.alexshabanov.gus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public final class DefaultUserService implements UserService {

    private final Logger log = LoggerFactory.getLogger(DefaultUserService.class);

    private final AccountService accountService;

    @Inject
    public DefaultUserService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public long registerNewUser() {
        return 1000 + accountService.registerNewAccount();
    }

    @Override
    public User findUser(long id) {
        // TODO: implement
        return null;
    }
}
