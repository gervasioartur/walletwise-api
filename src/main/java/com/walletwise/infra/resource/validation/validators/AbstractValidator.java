package com.walletwise.infra.resource.validation.validators;

import com.walletwise.infra.resource.validation.contract.IValidator;
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
