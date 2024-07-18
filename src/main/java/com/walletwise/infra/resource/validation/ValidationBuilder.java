package com.walletwise.infra.resource.validation;

import com.walletwise.infra.resource.validation.contract.IValidator;
import com.walletwise.infra.resource.validation.validators.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ValidationBuilder {
    private final List<IValidator> validators = new ArrayList<>();
    private String fieldName;
    private Object fieldValue;

    private ValidationBuilder(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static ValidationBuilder of(String fieldName, Object fieldValue) {
        return new ValidationBuilder(fieldName, fieldValue);
    }

    public ValidationBuilder required() {
        this.validators.add(new RequiredFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder email() {
        this.validators.add(new EmailFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder password() {
        this.validators.add(new PasswordFieldValidator(this.fieldName, this.fieldValue));
        return this;
    }

    public ValidationBuilder username() {
        this.validators.add(new UsernameFieldValidator(this.fieldValue));
        return this;
    }

    public ValidationBuilder min(double min) {
        this.validators.add(new MinDoubleFieldValidator(this.fieldName, this.fieldValue, min));
        return this;
    }

    public List<IValidator> build() {
        return this.validators;
    }
}