package com.walletwise.application.validation.validators;

public class DueDayFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public DueDayFieldValidator(Object fieldValue) {
        this.fieldValue = fieldValue;
        this.returnMessage = "Invalid due day! Expiration day must be between 1 to 31.";
    }

    @Override
    public String validate() {
        int value = (int) fieldValue;
        if (value > 31) return this.returnMessage;
        return null;
    }
}