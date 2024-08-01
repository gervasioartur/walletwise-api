package com.walletwise.application.validation.validators;

import com.walletwise.domain.entities.enums.PaymentFrequencyEnum;

public class PaymenteMethodFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public PaymenteMethodFieldValidator(Object fieldValue) {
        this.fieldValue = fieldValue;
        this.returnMessage = "Invalid Payment frequency! You must choose payment frequency between " +
                "DAILY,WEEKLY,MONTHLY or YEARLY.";
    }

    @Override
    public String validate() {
        String value = (String) fieldValue;
        if (!value.equals(PaymentFrequencyEnum.DAILY.getValue())
                && !value.equals(PaymentFrequencyEnum.WEEKLY.getValue())
                && !value.equals(PaymentFrequencyEnum.YEARLY.getValue())
                && !value.equals(PaymentFrequencyEnum.MONTHLY.getValue())
        ) return this.returnMessage;

        return null;
    }
}
