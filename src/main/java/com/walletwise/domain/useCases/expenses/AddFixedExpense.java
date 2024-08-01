package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;

public class AddFixedExpense {
    private final IExpenseAdapter expenseAdapter;

    public AddFixedExpense(IExpenseAdapter expenseAdapter) {
        this.expenseAdapter = expenseAdapter;
    }

    public void add(FixedExpense request) {
        this.expenseAdapter.add(request);
    }
}
