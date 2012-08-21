package com.alexshabanov.soaop.service.support;

import com.alexshabanov.soaop.domain.Account;
import com.alexshabanov.soaop.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public final class DefaultAccountService implements AccountService {

    @Override
    public long registerNewAccount() {
        // TODO: implement
        return -1L;
    }

    @Override
    public void updateAccount(Account account) {
        // TODO: implement
    }
}
