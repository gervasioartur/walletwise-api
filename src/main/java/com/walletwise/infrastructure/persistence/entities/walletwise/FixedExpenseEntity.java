package com.walletwise.infrastructure.persistence.entities.walletwise;

import com.walletwise.infrastructure.persistence.entities.security.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue("FIXED")
public class FixedExpenseEntity extends ExpenseEntity {

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public FixedExpenseEntity(UUID id, String description, String category, double amount, int dueDay, String paymentFrequency, UserEntity user, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, description, category, amount, dueDay, paymentFrequency, user, active, createdAt, updatedAt);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public FixedExpenseEntity(UUID id, String description, String category, double amount, int dueDay, String paymentFrequency, UserEntity user, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, description, category, amount, dueDay, paymentFrequency, user);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public String getCategory() {
        return super.getCategory();
    }

    @Override
    public void setCategory(String category) {
        super.setCategory(category);
    }

    @Override
    public int getDueDay() {
        return super.getDueDay();
    }

    @Override
    public void setDueDay(int dueDay) {
        super.setDueDay(dueDay);
    }

    @Override
    public String getPaymentFrequency() {
        return super.getPaymentFrequency();
    }

    @Override
    public void setPaymentFrequency(String paymentFrequency) {
        super.setPaymentFrequency(paymentFrequency);
    }

    @Override
    public UserEntity getUser() {
        return super.getUser();
    }

    @Override
    public void setUser(UserEntity user) {
        super.setUser(user);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }
}