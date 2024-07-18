package com.walletwise.infra.resource.validation;

import com.walletwise.infra.resource.validation.contract.IValidator;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ValidationComposite {
    private List<IValidator> validators;

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
