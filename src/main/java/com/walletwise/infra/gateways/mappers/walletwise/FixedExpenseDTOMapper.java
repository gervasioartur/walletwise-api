package com.walletwise.infra.gateways.mappers.walletwise;

import com.walletwise.application.dto.walletwise.AddFixedExpenseRequest;
import com.walletwise.application.dto.walletwise.FixedExpenseResponse;
import com.walletwise.domain.entities.models.FixedExpense;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FixedExpenseDTOMapper {

    public FixedExpense toFixedExpenseDomainObj(UUID userId, AddFixedExpenseRequest request) {
        return new FixedExpense(
                userId,
                request.description(),
                request.amount(),
                request.category(),
                request.dueDay(),
                request.startDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                request.endDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                request.paymentFrequency());
    }

    public FixedExpenseResponse toFixedExpenseResponse(FixedExpense request) {
        return new FixedExpenseResponse(
                request.getUserId(),
                request.getDescription(),
                request.getAmount(),
                request.getCategory(),
                request.getDueDay(),
                Date.from(request.getStartDate().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(request.getEndDate().atZone(ZoneId.systemDefault()).toInstant()),
                request.getPaymentFrequency());
    }

    public List<FixedExpenseResponse> toFixedExpenseListResponse(List<FixedExpense> requestList) {
        return requestList.stream()
                .map(this::toFixedExpenseResponse)
                .collect(Collectors.toList());
    }
}
