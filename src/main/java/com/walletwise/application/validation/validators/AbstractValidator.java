package com.walletwise.application.validation.validators;

import com.walletwise.application.validation.contract.IValidator;

public abstract class AbstractValidator implements IValidator {
    protected String fieldName;
    protected Object fieldValue;

    @Override
    public String validate() {
        return null;
    }
}
