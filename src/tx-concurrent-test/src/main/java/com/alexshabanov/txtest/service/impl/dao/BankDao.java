package com.alexshabanov.txtest.service.impl.dao;

import com.alexshabanov.txtest.service.model.BalanceChange;
import com.alexshabanov.txtest.service.model.BalanceChangeEntry;

import java.util.Collection;

/**
 * Interface to Bank DAO.
 */
public interface BankDao {
    long addUser(String name);

    double getBalance(long userId);

    void setBalance(long userId, double balance);

    void addToBalance(long userId, double amount);

    long addBalanceChange(long userId, double amount, BalanceChange balanceChange);

    Collection<BalanceChangeEntry> getBalanceChanges(long userId);
}
