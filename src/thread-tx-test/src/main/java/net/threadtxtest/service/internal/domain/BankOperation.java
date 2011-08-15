package net.threadtxtest.service.internal.domain;

import net.threadtxtest.service.BankOperationStatus;

import java.math.BigDecimal;

/**
 * Represents bank operation.
 */
public class BankOperation {
    private long id;

    private long userId;

    private BankOperationStatus status;

    private BigDecimal amount;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BankOperationStatus getStatus() {
        return status;
    }

    public void setStatus(BankOperationStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
