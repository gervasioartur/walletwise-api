package com.walletwise.application.validation;

import com.walletwise.application.validation.contract.IValidator;

import java.util.List;

public class ValidationComposite {
    private final List<IValidator> validators;

    public ValidationComposite(List<IValidator> validators) {
        this.validators = validators;
    }

    public String validate() {
        for (IValidator validator : validators) {
            String error = validator.validate();
            if (error != null)
                return error;
        }
        return null;
    }
}
