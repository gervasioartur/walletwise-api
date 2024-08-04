package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;

import java.io.OutputStream;
import java.util.UUID;

public class GenerateFixedExpensesReport {
    private final IExpenseAdapter expenseAdapter;

    public GenerateFixedExpensesReport(IExpenseAdapter expenseAdapter) {
        this.expenseAdapter = expenseAdapter;
    }

    public void generate(UUID userId, OutputStream outputStream) throws Exception {
        this.expenseAdapter.generateFixedExpensesReport(userId, outputStream);
    }
}
