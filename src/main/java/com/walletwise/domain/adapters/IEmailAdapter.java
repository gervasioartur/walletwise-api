package com.walletwise.domain.adapters;

public interface IEmailAdapter {
    void sendEmail(String receptor, String message, String subject);
}