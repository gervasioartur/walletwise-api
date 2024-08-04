package com.walletwise.application.validation.validators;

import com.walletwise.domain.entities.enums.ExpenseCategoryEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpenseCategoryFieldValidatorTests {
    private ExpenseCategoryFieldValidator validator;

    @Test
    @DisplayName("Should return error message if category is invalid")
    void shouldReturnErrorMessageIfCategoryIsInvalid() {
        this.validator = new ExpenseCategoryFieldValidator("invalid");

        String result = this.validator.validate();
        Assertions.assertThat(result).isEqualTo("Invalid category! You must choose a category between " +
                "FOOD,TRANSPORT,RENT,ENTERTAINMENT,SCHOOL or OTHERS.");
    }

    @Test
    @DisplayName("Sgould return if category is valid")
    void shouldReturnErrorMessageIfCategoryIsValid() {
        this.validator = new ExpenseCategoryFieldValidator(ExpenseCategoryEnum.RENT.getValue());
        String result = this.validator.validate();
        Assertions.assertThat(result).isNull();

        this.validator = new ExpenseCategoryFieldValidator(ExpenseCategoryEnum.FOOD.getValue());
        result = this.validator.validate();
        Assertions.assertThat(result).isNull();

        this.validator = new ExpenseCategoryFieldValidator(ExpenseCategoryEnum.OTHER.getValue());
        result = this.validator.validate();
        Assertions.assertThat(result).isNull();

        this.validator = new ExpenseCategoryFieldValidator(ExpenseCategoryEnum.SCHOOL.getValue());
        result = this.validator.validate();
        Assertions.assertThat(result).isNull();

        this.validator = new ExpenseCategoryFieldValidator(ExpenseCategoryEnum.TRANSPORT.getValue());
        result = this.validator.validate();
        Assertions.assertThat(result).isNull();

        this.validator = new ExpenseCategoryFieldValidator(ExpenseCategoryEnum.ENTERTAINMENT.getValue());
        result = this.validator.validate();
        Assertions.assertThat(result).isNull();
    }
}
