package com.walletwise.domain.entities.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ValidationToken {
    private UUID id;
    private String token;
    private LocalDateTime expirationDate;

    public ValidationToken(String token) {
        this.token = token;
    }

    public ValidationToken(String token, LocalDateTime expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public ValidationToken(UUID id, String token, LocalDateTime expirationDate) {
        this.id = id;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public UUID getId() {
        return id;
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
}
