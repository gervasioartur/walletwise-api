package com.walletwise.application.http;

public record ConfirmPasswordRecoveryRequest(String token, String newPassword) {
}
