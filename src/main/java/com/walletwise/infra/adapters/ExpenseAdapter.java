package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.infra.persistence.repositories.walletwise.IFixedExpenseRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.UUID;

public class ExpenseAdapter implements IExpenseAdapter {
    private final IFixedExpenseRepository fixedExpenseRepository;
    private final FixedExpenseEntityMapper fixedExpenseEntityMapper;

    public ExpenseAdapter(IFixedExpenseRepository fixedExpenseRepository, FixedExpenseEntityMapper fixedExpenseEntityMapper) {
        this.fixedExpenseRepository = fixedExpenseRepository;
        this.fixedExpenseEntityMapper = fixedExpenseEntityMapper;
    }

    @Override
    @CacheEvict(value = "fixed_expenses", key = "#request.userId")
    public FixedExpense add(FixedExpense request) {
        FixedExpenseEntity entity = this.fixedExpenseEntityMapper.toFixedExpenseEntity(request);
        entity.setActive(true);
        entity = this.fixedExpenseRepository.save(entity);
        return this.fixedExpenseEntityMapper.toFixedExpense(entity);
    }

    @Override
    @Cacheable(value = "fixed_expenses", key = "#userId")
    public List<FixedExpense> getByUserId(UUID userId) {
        List<FixedExpenseEntity> entityList = this.fixedExpenseRepository.findByUserId(userId);
        return this.fixedExpenseEntityMapper.toFixedExpenseList(entityList);
    }
}
