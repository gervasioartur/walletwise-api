package com.walletwise.domain.entities.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ValidationToken {
    private UUID id;
    private UUID userId;
    private String token;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;

    public ValidationToken(String token) {
        this.token = token;
    }

    public ValidationToken(UUID userId, String token, LocalDateTime expirationDate) {
        this.userId = userId;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public ValidationToken(UUID id, UUID userId, String token, LocalDateTime expirationDate) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public ValidationToken(UUID id, UUID userId, String token, LocalDateTime expirationDate, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
