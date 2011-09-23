package com.alexshabanov.service.impl.dao;


import com.alexshabanov.service.domain.UserAccount;

import java.math.BigDecimal;
import java.util.List;

public interface UserAccountDao {
    int addUserAccount(String name, BigDecimal balance);

    List<UserAccount> getUserAccounts(int offset, int limit);

    UserAccount getUserAccountByName(String name);

    void deleteUser(int id);
}
