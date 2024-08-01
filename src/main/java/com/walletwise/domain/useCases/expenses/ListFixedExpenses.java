package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;

import java.util.List;
import java.util.UUID;

public class ListFixedExpenses {
    private final  IExpenseAdapter expenseAdapter;

    public ListFixedExpenses(IExpenseAdapter expenseAdapter) {
        this.expenseAdapter = expenseAdapter;
    }

    public List<FixedExpense> list(UUID userId){
        return this.expenseAdapter.getByUserId(userId);
    }
}
