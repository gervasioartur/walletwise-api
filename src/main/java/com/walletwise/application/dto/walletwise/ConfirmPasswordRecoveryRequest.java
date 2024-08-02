package com.walletwise.application.dto.walletwise;

public record ConfirmPasswordRecoveryRequest(String token, String newPassword) {
}
