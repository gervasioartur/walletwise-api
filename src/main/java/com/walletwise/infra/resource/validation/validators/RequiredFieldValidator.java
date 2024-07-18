package com.walletwise.infra.resource.validation.validators;

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
        } else if (this.fieldValue instanceof Integer) {
            return this.fieldValue.equals(0) ? this.returnMessage : null;
        } else {
            return this.fieldValue == null ? this.returnMessage : null;
        }
    }
}
