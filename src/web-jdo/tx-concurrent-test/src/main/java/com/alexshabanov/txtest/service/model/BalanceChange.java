package com.alexshabanov.txtest.service.model;

public enum BalanceChange {
    REPLENISHMENT(0),
    WITHDRAWAL(1);

    private final int value;

    public int getValue() {
        return value;
    }

    public static BalanceChange getEnumByValue(int value) {
        for (final BalanceChange enumVal : BalanceChange.values()) {
            if (enumVal.getValue() == value) {
                return enumVal;
            }
        }

        throw new IllegalArgumentException("Invalid value: " + value);
    }

    BalanceChange(int value) {
        this.value = value;
    }
}
