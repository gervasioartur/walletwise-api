package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.gateways.mappers.FixedExpenseEntityMapper;
import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.infra.persistence.repositories.security.IFixedExpenseRepository;

public class ExpenseAdapter implements IExpenseAdapter {
    private final IFixedExpenseRepository fixedExpenseRepository;
    private final FixedExpenseEntityMapper fixedExpenseEntityMapper;

    public ExpenseAdapter(IFixedExpenseRepository fixedExpenseRepository, FixedExpenseEntityMapper fixedExpenseEntityMapper) {
        this.fixedExpenseRepository = fixedExpenseRepository;
        this.fixedExpenseEntityMapper = fixedExpenseEntityMapper;
    }

    @Override
    public FixedExpense add(FixedExpense request) {
        FixedExpenseEntity entity = this.fixedExpenseEntityMapper.toFixedExpenseEntity(request);
        entity = this.fixedExpenseRepository.save(entity);
        return this.fixedExpenseEntityMapper.toFixedExpense(entity);
    }
}
