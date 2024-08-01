package com.walletwise.application.validation.validators;

import java.util.Date;

public class EndDateFieldValidator extends AbstractValidator {
    private final String returnMessage;
    private final Date startDate;

    public EndDateFieldValidator(Date startDate, Object fieldValue) {
        this.fieldValue = fieldValue;
        this.startDate = startDate;
        this.returnMessage = "The end date must be after start date.";
    }

    @Override
    public String validate() {
        Date endDate = (Date) fieldValue;
        if (endDate.before(startDate)) return this.returnMessage;
        return null;
    }
}