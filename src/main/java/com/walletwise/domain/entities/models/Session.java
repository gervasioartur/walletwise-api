package com.walletwise.domain.entities.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    private UUID id;

    private String token;

    private User user;

    private boolean active;

    private LocalDateTime expirationDate;

    private LocalDateTime createdAt;

    public Session(String token, User user, boolean active, LocalDateTime expirationDate, LocalDateTime createdAt) {
        this.token = token;
        this.user = user;
        this.active = active;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public Session(UUID id, String token, User user, boolean active, LocalDateTime expirationDate, LocalDateTime createdAt) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.active = active;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }

    public Session(String token, User user, boolean active, LocalDateTime expirationDate) {
        this.token = token;
        this.user = user;
        this.active = active;
        this.expirationDate = expirationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
