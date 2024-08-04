package com.walletwise.infrastructure.gateways.mappers.walletwise;

import com.walletwise.domain.entities.models.walletwise.FixedExpense;
import com.walletwise.infrastructure.persistence.entities.security.UserEntity;
import com.walletwise.infrastructure.persistence.entities.walletwise.FixedExpenseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FixedExpenseEntityMapper {
    public FixedExpenseEntity toFixedExpenseEntity(FixedExpense fixedExpense) {
        return new FixedExpenseEntity(
                fixedExpense.getId(),
                fixedExpense.getDescription(),
                fixedExpense.getCategory(),
                fixedExpense.getAmount(),
                fixedExpense.getDueDay(),
                fixedExpense.getPaymentFrequency(),
                UserEntity.builder().id(fixedExpense.getUserId()).build(),
                fixedExpense.getStartDate(),
                fixedExpense.getEndDate());
    }

    public FixedExpense toFixedExpense(FixedExpenseEntity entity) {
        return new FixedExpense(
                entity.getId(),
                entity.getUser().getId(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getCategory(),
                entity.getDueDay(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPaymentFrequency());
    }

    public List<FixedExpense> toFixedExpenseList(List<FixedExpenseEntity> expenseEntity) {
        return expenseEntity.stream()
                .map(this::toFixedExpense)
                .collect(Collectors.toList());
    }
}
