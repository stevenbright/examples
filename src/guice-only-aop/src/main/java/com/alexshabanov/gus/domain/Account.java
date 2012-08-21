package com.alexshabanov.gus.domain;

public class Account {
    private long id;
    private int userId;
    private String name;
    private long moneyAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id == account.id && moneyAmount == account.moneyAmount && userId == account.userId && !(name != null ? !name.equals(account.name) : account.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (moneyAmount ^ (moneyAmount >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
