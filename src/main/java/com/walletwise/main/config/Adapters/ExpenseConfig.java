package com.walletwise.main.config.Adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.useCases.expenses.AddFixedExpense;
import com.walletwise.domain.useCases.expenses.GenerateFixedExpensesReport;
import com.walletwise.domain.useCases.expenses.ListFixedExpenses;
import com.walletwise.infrastructure.adapters.ExpenseAdapter;
import com.walletwise.infrastructure.gateways.helpers.JasperReportHelper;
import com.walletwise.infrastructure.gateways.mappers.walletwise.FixedExpenseDTOMapper;
import com.walletwise.infrastructure.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infrastructure.persistence.repositories.walletwise.IFixedExpenseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseConfig {

    @Bean
    public AddFixedExpense addFixedExpense(IExpenseAdapter expenseAdapter) {
        return new AddFixedExpense(expenseAdapter);
    }

    @Bean
    public ListFixedExpenses listFixedExpenses(IExpenseAdapter expenseAdapter) {
        return new ListFixedExpenses(expenseAdapter);
    }

    @Bean
    public GenerateFixedExpensesReport generateFixedExpensesReport(IExpenseAdapter expenseAdapter) {
        return new GenerateFixedExpensesReport(expenseAdapter);
    }

    @Bean
    public FixedExpenseDTOMapper fixedExpenseDTOMapper() {
        return new FixedExpenseDTOMapper();
    }

    @Bean
    public FixedExpenseEntityMapper fixedExpenseEntityMapper() {
        return new FixedExpenseEntityMapper();
    }

    @Bean
    public ExpenseAdapter expenseAdapter(IFixedExpenseRepository fixedExpenseRepository,
                                         FixedExpenseEntityMapper fixedExpenseEntityMapper,
                                         JasperReportHelper jasperReportHelper) {
        return new ExpenseAdapter(fixedExpenseRepository, fixedExpenseEntityMapper, jasperReportHelper);
    }
}
