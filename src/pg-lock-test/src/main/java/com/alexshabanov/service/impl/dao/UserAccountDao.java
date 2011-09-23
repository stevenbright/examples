package com.alexshabanov.service.impl.dao;


import com.alexshabanov.service.domain.UserAccount;

import java.math.BigDecimal;
import java.util.List;

public interface UserAccountDao {
    int addUserAccount(String name, BigDecimal balance);

    List<UserAccount> getUserAccounts(int offset, int limit);

    UserAccount getUserAccountById(int id);

    UserAccount getUserAccountByName(String name);

    UserAccount getUserAccountByIdForUpdate(int id);

    void updateUserBalance(int id, BigDecimal newBalance);

    void deleteUser(int id);
}
