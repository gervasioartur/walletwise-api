package com.walletwise.application.validation.validators;

import com.walletwise.domain.entities.enums.ExpenseCategoryEnum;

public class ExpenseCategoryFieldValidator extends AbstractValidator {
    private final String returnMessage;

    public ExpenseCategoryFieldValidator(Object fieldValue) {
        this.fieldValue = fieldValue;
        this.returnMessage = "Invalid category! You must choose a category between " +
                "FOOD,TRANSPORT,RENT,ENTERTAINMENT,SCHOOL or OTHERS.";
    }

    @Override
    public String validate() {
        String value = (String) fieldValue;
        if(!value.equals(ExpenseCategoryEnum.RENT.getValue())
                && !value.equals(ExpenseCategoryEnum.FOOD.getValue())
                && !value.equals(ExpenseCategoryEnum.OTHER.getValue())
                && !value.equals(ExpenseCategoryEnum.SCHOOL.getValue())
                && !value.equals(ExpenseCategoryEnum.TRANSPORT.getValue())
                && !value.equals(ExpenseCategoryEnum.ENTERTAINMENT.getValue())
        ) return this.returnMessage;

        return null;
    }
}
