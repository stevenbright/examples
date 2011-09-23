package com.alexshabanov.service;

import com.alexshabanov.service.domain.UserAccount;

import java.math.BigDecimal;

public interface UserService {
    void setUpdateOptions(long delayMillis, boolean pessimisticLock);

    int addUserAccount(String name, BigDecimal balance);

    UserAccount getUserAccount(String name);

    void addToUserBalance(int id, BigDecimal balanceDelta);

    void deleteAllUsers();
}
