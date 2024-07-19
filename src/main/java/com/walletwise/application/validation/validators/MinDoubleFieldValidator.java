package com.walletwise.application.validation.validators;

public class MinDoubleFieldValidator extends AbstractValidator {
    private final String returnMessage;
    private final double min;

    public MinDoubleFieldValidator(String fieldName, Object fieldValue, double min) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.min = min;
        this.returnMessage = "The field '" + this.fieldName + "' must be greater  than " + this.min + "!";
    }

    @Override
    public String validate() {
        double value = (double) this.fieldValue;
        if (value < min)
            return returnMessage;
        return null;
    }
}