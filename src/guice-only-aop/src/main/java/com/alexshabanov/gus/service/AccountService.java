package com.alexshabanov.gus.service;

import com.alexshabanov.gus.domain.Account;

public interface AccountService {
    long registerNewAccount();

    void updateAccount(Account account);
}
