package com.walletwise.infrastructure.api.validation;


import com.walletwise.infrastructure.api.validation.validor.contract.IValidator;
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

    public List<IValidator> build() {
        return this.validators;
    }
}