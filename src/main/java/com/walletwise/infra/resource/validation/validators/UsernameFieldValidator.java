package com.walletwise.infra.resource.validation.validators;

public class UsernameFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public UsernameFieldValidator(Object fieldValue) {
        this.fieldValue = fieldValue;
        this.returnMessage = "Invalid Username! The username should not start with special character.";
    }

    @Override
    public String validate() {
        String username = (String) this.fieldValue;
        if (username.contains("@"))
            return returnMessage;
        return null;
    }
}
