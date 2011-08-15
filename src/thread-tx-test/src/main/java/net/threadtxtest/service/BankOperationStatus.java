package net.threadtxtest.service;

/**
 * Represents the possible operation statuses.
 */
public enum BankOperationStatus {
    PENDING(1),
    SUCCEEDED(2);

    private final int value;

    public int getValue() {
        return value;
    }

    public static BankOperationStatus getEnumByValue(int value) {
        for (final BankOperationStatus status : BankOperationStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unsupported value: " + value);
    }

    BankOperationStatus(int value) {
        this.value = value;
    }
}
