package com.alexshabanov.service.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents user account.
 */
public final class UserAccount {
    private final int id;
    private final String name;
    private final BigDecimal balance;
    private final Date created;

    public UserAccount(int id, String name, BigDecimal balance, Date created) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Date getCreated() {
        return created;
    }
}
