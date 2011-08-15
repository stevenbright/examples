package com.alexshabanov.txtest.service;

import com.alexshabanov.txtest.service.model.BalanceChangeEntry;

import java.util.Collection;

/**
 * Represents service facade.
 */
public interface BankService {
    long registerUser(String name);

    double getBalance(long userId);

    void replenish(long userId, double amount);

    void withdraw(long userId, double amount);

    void setMaxReplenishmentAmount(double maxReplenishmentAmount);

    double getMaxReplenishmentAmount();

    Collection<BalanceChangeEntry> queryBalanceChanges(long userId);
}
