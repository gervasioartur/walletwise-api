package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.exceptions.UnexpectedException;
import io.sentry.Sentry;

import java.io.OutputStream;
import java.util.UUID;

public class GenerateFixedExpensesReport {
    private final IExpenseAdapter expenseAdapter;

    public GenerateFixedExpensesReport(IExpenseAdapter expenseAdapter) {
        this.expenseAdapter = expenseAdapter;
    }

    public void generate(UUID userId, OutputStream outputStream) {
        try {
            this.expenseAdapter.generateFixedExpensesReport(userId, outputStream);
        } catch (Exception ex) {
            Sentry.captureException(ex);
            throw new UnexpectedException(ex.getMessage());
        }
    }
}
