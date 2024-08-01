package com.walletwise.application.http;

import java.util.Date;

public record AddFixedExpenseRequest(
        String description,
        double amount,
        String category,
        int dueDay,
        Date startDate,
        Date endDate,
        String paymentFrequency
) {
}
