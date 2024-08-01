package com.walletwise.infra.gateways.mappers.walletwise;

import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;

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
}
