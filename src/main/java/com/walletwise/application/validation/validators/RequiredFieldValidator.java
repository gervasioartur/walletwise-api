package com.walletwise.application.validation.validators;

public class RequiredFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public RequiredFieldValidator(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.returnMessage = this.fieldName + " is required.";
    }

    @Override
    public String validate() {
        if (this.fieldValue instanceof String) {
            return ((String) this.fieldValue).trim().isEmpty() ? this.returnMessage : null;
        }
        return this.returnMessage;
    }
}
