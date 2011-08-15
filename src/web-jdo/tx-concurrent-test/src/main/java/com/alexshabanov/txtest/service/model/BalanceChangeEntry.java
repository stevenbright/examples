package com.alexshabanov.txtest.service.model;


public class BalanceChangeEntry {
    private long id;

    private double amount;

    private BalanceChange balanceChange;

    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BalanceChange getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BalanceChange balanceChange) {
        this.balanceChange = balanceChange;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BalanceChangeEntry");
        sb.append("{id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", balanceChange=").append(balanceChange);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
