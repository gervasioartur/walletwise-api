package com.walletwise.infra.gateways.mappers.walletwise;

import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

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

    @Test
    @DisplayName("Should return fixed expense domain object")
    void shouldReturnFixedExpenseDomainObject() {
        FixedExpenseEntity fixedExpenseEntity = Mocks.fixedExpenseEntityFactory();
        FixedExpense result = this.mapper.toFixedExpense(fixedExpenseEntity);

        Assertions.assertThat(result.getId()).isEqualTo(fixedExpenseEntity.getId());
        Assertions.assertThat(result.getDescription()).isEqualTo(fixedExpenseEntity.getDescription());
        Assertions.assertThat(result.getCategory()).isEqualTo(fixedExpenseEntity.getCategory());
        Assertions.assertThat(result.getDueDay()).isEqualTo(fixedExpenseEntity.getDueDay());
        Assertions.assertThat(result.getPaymentFrequency()).isEqualTo(fixedExpenseEntity.getPaymentFrequency());
        Assertions.assertThat(result.getUserId()).isEqualTo(fixedExpenseEntity.getUser().getId());
        Assertions.assertThat(result.getStartDate()).isEqualTo(fixedExpenseEntity.getStartDate());
        Assertions.assertThat(result.getEndDate()).isEqualTo(fixedExpenseEntity.getEndDate());
    }

    @Test
    @DisplayName("Should return List of fixed expense")
    void shouldReturnListOfFixedExpense() {
        UUID userId = UUID.randomUUID();
        List<FixedExpenseEntity> expenseEntities = Mocks.fixedExpenseEntityListFactory(userId);
        List<FixedExpense> result = this.mapper.toFixedExpenseList(expenseEntities);
        Assertions.assertThat(result.size()).isEqualTo(expenseEntities.size());
        Assertions.assertThat(result.get(0).getId()).isEqualTo(expenseEntities.get(0).getId());
    }
}
