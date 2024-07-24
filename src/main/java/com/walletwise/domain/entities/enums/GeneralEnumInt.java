package com.walletwise.domain.entities.enums;

public enum GeneralEnumInt {
    EXPIRE_TIME_OF_VALIDATION_CODE(15),
    MIN_VALIDATION_CODE_LENGTH(100000),
    MAX_VALIDATION_CODE_LENGTH(900000),
    JWT_TOKEN_EXPIRATION(1);
    private final int value;

    GeneralEnumInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}