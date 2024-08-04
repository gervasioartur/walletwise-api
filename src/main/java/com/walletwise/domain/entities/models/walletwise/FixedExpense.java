package com.walletwise.domain.entities.models.walletwise;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedExpense implements Serializable {
    private UUID id;
    private UUID userId;
    private String description;
    private double amount;
    private String category;
    private int dueDay;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String paymentFrequency;

    public FixedExpense(UUID userId, String description, double amount, String category, int dueDay, LocalDateTime startDate, LocalDateTime endDate, String paymentFrequency) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.dueDay = dueDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentFrequency = paymentFrequency;
    }

    public FixedExpense(UUID id, UUID userId, String description, double amount, String category, int dueDay, LocalDateTime startDate, LocalDateTime endDate, String paymentFrequency) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.dueDay = dueDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentFrequency = paymentFrequency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDueDay() {
        return dueDay;
    }

    public void setDueDay(int dueDay) {
        this.dueDay = dueDay;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
