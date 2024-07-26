package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.persistence.entities.FixedExpenseEntity;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FixedExpenseEntityMapperTests {
    private FixedExpenseEntityMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new FixedExpenseEntityMapper();
    }

    @Test
    @DisplayName("Should return fixed expense entity")
    void shouldReturnFixedExpenseEntity() {
        FixedExpense fixedExpense = Mocks.fixedExpenseWithNoIdFactory();
        FixedExpenseEntity result = this.mapper.toFixedExpenseEntity(fixedExpense);

        Assertions.assertThat(result.getId()).isEqualTo(fixedExpense.getId());
        Assertions.assertThat(result.getDescription()).isEqualTo(fixedExpense.getDescription());
        Assertions.assertThat(result.getCategory()).isEqualTo(fixedExpense.getCategory());
        Assertions.assertThat(result.getDueDay()).isEqualTo(fixedExpense.getDueDay());
        Assertions.assertThat(result.getPaymentFrequency()).isEqualTo(fixedExpense.getPaymentFrequency());
        Assertions.assertThat(result.getUser().getId()).isEqualTo(fixedExpense.getUserId());
        Assertions.assertThat(result.getStartDate()).isEqualTo(fixedExpense.getStartDate());
        Assertions.assertThat(result.getEndDate()).isEqualTo(fixedExpense.getEndDate());
    }
}
