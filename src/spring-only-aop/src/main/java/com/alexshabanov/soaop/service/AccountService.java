package com.alexshabanov.soaop.service;

import com.alexshabanov.soaop.domain.Account;

public interface AccountService {
    long registerNewAccount();

    void updateAccount(Account account);
}
