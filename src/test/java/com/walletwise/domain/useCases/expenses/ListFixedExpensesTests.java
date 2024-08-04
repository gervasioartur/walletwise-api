package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.walletwise.FixedExpense;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class ListFixedExpensesTests {
    private ListFixedExpenses listFixedExpenses;
    @MockBean
    private IExpenseAdapter expenseAdapter;

    @BeforeEach
    void setUp() {
        listFixedExpenses = new ListFixedExpenses(expenseAdapter);
    }

    @Test
    @DisplayName("Should return user list expenses")
    void shouldUserListExpenses() {
        UUID userId = UUID.randomUUID();
        List<FixedExpense> list = Mocks.fixedExpenseListFactory(userId);
        Mockito.when(this.expenseAdapter.getByUserId(userId)).thenReturn(list);
        List<FixedExpense> result = this.listFixedExpenses.list(userId);
        Assertions.assertThat(result).isEqualTo(list);
        Mockito.verify(this.expenseAdapter).getByUserId(userId);
    }
}
