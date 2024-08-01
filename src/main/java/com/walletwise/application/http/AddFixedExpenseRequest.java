package com.walletwise.application.http;

import java.util.Date;

public record AddFixedExpenseRequest(
        String description,
        Double amount,
        String category,
        int dueDay,
        Date startDate,
        Date endDate,
        String paymentFrequency
) {
}
