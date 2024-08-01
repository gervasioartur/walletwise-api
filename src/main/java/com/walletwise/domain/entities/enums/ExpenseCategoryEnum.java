package com.walletwise.domain.entities.enums;

public enum ExpenseCategoryEnum {
    RENT("RENT"),
    FOOD("FOOD"),
    OTHER("OTHER"),
    SCHOOL("SCHOOL"),
    TRANSPORT("TRANSPORT"),
    ENTERTAINMENT("ENTERTAINMENT");

    private final String value;

    ExpenseCategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
