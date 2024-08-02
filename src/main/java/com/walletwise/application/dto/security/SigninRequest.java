package com.walletwise.application.dto.security;

public record SigninRequest(String usernameOrEmail, String password) {
}
