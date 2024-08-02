package com.walletwise.application.dto.walletwise;

import java.util.Date;
import java.util.UUID;

public record FixedExpenseResponse(
        UUID id,
        String description,
        Double amount,
        String category,
        int dueDay,
        Date startDate,
        Date endDate,
        String paymentFrequency
) {
}
