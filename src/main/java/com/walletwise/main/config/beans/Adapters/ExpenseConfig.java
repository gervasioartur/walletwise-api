package com.walletwise.main.config.beans.Adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.useCases.expenses.AddFixedExpense;
import com.walletwise.domain.useCases.expenses.ListFixedExpenses;
import com.walletwise.infra.adapters.ExpenseAdapter;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseDTOMapper;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infra.persistence.repositories.security.IFixedExpenseRepository;
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
    public FixedExpenseDTOMapper fixedExpenseDTOMapper() {
        return new FixedExpenseDTOMapper();
    }

    @Bean
    public FixedExpenseEntityMapper fixedExpenseEntityMapper() {
        return new FixedExpenseEntityMapper();
    }

    @Bean
    public ExpenseAdapter expenseAdapter(IFixedExpenseRepository fixedExpenseRepository, FixedExpenseEntityMapper fixedExpenseEntityMapper) {
        return new ExpenseAdapter(fixedExpenseRepository, fixedExpenseEntityMapper);
    }
}
