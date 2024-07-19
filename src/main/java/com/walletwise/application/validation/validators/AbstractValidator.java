package com.walletwise.application.validation.validators;

import com.walletwise.application.validation.contract.IValidator;
import com.walletwise.main.config.annotations.Generated;

@Generated
public abstract class AbstractValidator implements IValidator {
    protected String fieldName;
    protected Object fieldValue;

    @Override
    public String validate() {
        return null;
    }
}
