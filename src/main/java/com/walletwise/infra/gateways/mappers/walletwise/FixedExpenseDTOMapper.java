package com.walletwise.infra.gateways.mappers.walletwise;

import com.walletwise.application.http.AddFixedExpenseRequest;
import com.walletwise.domain.entities.models.FixedExpense;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class FixedExpenseDTOMapper {

    public FixedExpense toFixedExpenseDomainObj(UUID userId, AddFixedExpenseRequest request) {
        LocalDateTime startDate = request.startDate() == null ? LocalDateTime.now()
                :  request.startDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new FixedExpense(
                userId,
                request.description(),
                request.amount(),
                request.category(),
                request.dueDay(),
                startDate,
                request.endDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                request.paymentFrequency());
    }
}
