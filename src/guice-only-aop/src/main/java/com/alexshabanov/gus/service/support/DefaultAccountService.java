package com.alexshabanov.gus.service.support;

import com.alexshabanov.gus.domain.Account;
import com.alexshabanov.gus.service.AccountService;

public final class DefaultAccountService implements AccountService {

    private long startId = 100;

    @Override
    public long registerNewAccount() {
        ++startId;
        return startId;
    }

    @Override
    public void updateAccount(Account account) {
        // TODO: implement
    }
}
