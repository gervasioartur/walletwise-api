package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.mocks.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AddFixedExpenseTests {
    private AddFixedExpense addFixedExpense;
    @MockBean
    private IExpenseAdapter expenseAdapter;

    @BeforeEach
    void setUp() {
        this.addFixedExpense = new AddFixedExpense(expenseAdapter);
    }

    @Test
    @DisplayName("Should add fixed expense")
    void shouldAddFixedExpense() {
        FixedExpense fixedExpense = Mocks.fixedExpenseWithNoIdFactory();
        Mockito.doNothing().when(this.expenseAdapter).add(fixedExpense);
        this.addFixedExpense.add(fixedExpense);
        Mockito.verify(this.expenseAdapter, Mockito.times(1)).add(fixedExpense);
    }
}
