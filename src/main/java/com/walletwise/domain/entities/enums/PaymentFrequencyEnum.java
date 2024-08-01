package com.walletwise.domain.entities.enums;

public enum PaymentFrequencyEnum {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    YEARLY("YEARLY"),
    MONTHLY("MONTHLY");

    private final String value;

    PaymentFrequencyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
