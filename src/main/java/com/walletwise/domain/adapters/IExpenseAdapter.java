package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.FixedExpense;

import java.util.List;
import java.util.UUID;

public interface IExpenseAdapter {
    FixedExpense add(FixedExpense request);

    List<FixedExpense> getByUserId(UUID userId);
}
