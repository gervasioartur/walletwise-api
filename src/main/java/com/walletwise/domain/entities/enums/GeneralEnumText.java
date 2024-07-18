package com.walletwise.domain.entities.enums;

public enum GeneralEnumText {
    PASSWORD_REGEX_EXPRESSION("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,32}$"),
    EMAIL_PASSWORD_EXPRESSION("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final String value;

    GeneralEnumText(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
