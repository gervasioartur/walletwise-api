package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.infra.persistence.repositories.security.IFixedExpenseRepository;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class ExpenseAdapterTests {
    private IExpenseAdapter expenseAdapter;
    @MockBean
    private IFixedExpenseRepository fixedExpenseRepository;
    @MockBean
    private FixedExpenseEntityMapper fixedExpenseEntityMapper;

    @BeforeEach
    public void setUp() {
        expenseAdapter = new ExpenseAdapter(fixedExpenseRepository, fixedExpenseEntityMapper);
    }

    @Test
    @DisplayName("Should add fixed expense")
    void shouldAddFixedExpense() {
        FixedExpense fixedExpense = Mocks.fixedExpenseWithNoIdFactory();
        FixedExpenseEntity fixedExpenseEntity = Mocks.formFixedExpenseToEntity(fixedExpense);

        FixedExpenseEntity savedFixedExpenseEntity = Mocks.formFixedExpenseToEntity(fixedExpense);
        savedFixedExpenseEntity.setId(UUID.randomUUID());

        Mockito.when(this.fixedExpenseEntityMapper.toFixedExpenseEntity(fixedExpense)).thenReturn(fixedExpenseEntity);
        Mockito.when(this.fixedExpenseRepository.save(fixedExpenseEntity)).thenReturn(savedFixedExpenseEntity);
        Mockito.when(this.fixedExpenseEntityMapper.toFixedExpense(savedFixedExpenseEntity)).thenReturn(fixedExpense);

        FixedExpense result = this.expenseAdapter.add(fixedExpense);

        Assertions.assertThat(result.getId()).isEqualTo(fixedExpense.getId());
        Assertions.assertThat(result.getUserId()).isEqualTo(fixedExpense.getUserId());
        Mockito.verify(this.fixedExpenseEntityMapper, Mockito.times(1)).toFixedExpenseEntity(fixedExpense);
        Mockito.verify(this.fixedExpenseRepository, Mockito.times(1)).save(fixedExpenseEntity);
        Mockito.verify(this.fixedExpenseEntityMapper, Mockito.times(1)).toFixedExpense(savedFixedExpenseEntity);
    }
}
