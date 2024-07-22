package com.walletwise.domain.adapters;

import java.rmi.UnexpectedException;

public interface IEmailAdapter {
    void sendEmail(String receptor, String message, String subject);
}