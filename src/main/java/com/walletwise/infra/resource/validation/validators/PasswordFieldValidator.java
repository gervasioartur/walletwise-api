package com.walletwise.infra.resource.validation.validators;

import com.walletwise.domain.entities.enums.GeneralEnumText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public PasswordFieldValidator(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.returnMessage = "Password too weak! Must contain at least 8 characters, one uppercase letter, a special character and a number.";
    }

    @Override
    public String validate() {
        String value = (String) this.fieldValue;
        value = value.trim();

        String regex = GeneralEnumText.PASSWORD_REGEX_EXPRESSION.getValue();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
            return this.returnMessage;
        return null;
    }

}
