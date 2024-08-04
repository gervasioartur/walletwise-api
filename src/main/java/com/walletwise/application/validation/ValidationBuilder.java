package com.walletwise.application.validation;

import com.walletwise.application.validation.contract.IValidator;
import com.walletwise.application.validation.validators.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public ValidationBuilder expenseCategory() {
        this.validators.add(new ExpenseCategoryFieldValidator(this.fieldValue));
        return this;
    }

    public ValidationBuilder dueDay() {
        this.validators.add(new DueDayFieldValidator(this.fieldValue));
        return this;
    }

    public ValidationBuilder endDate(Date startDate) {
        this.validators.add(new EndDateFieldValidator(startDate, this.fieldValue));
        return this;
    }

    public List<IValidator> build() {
        return this.validators;
    }
}