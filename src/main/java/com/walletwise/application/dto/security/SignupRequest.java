package com.walletwise.application.dto.security;

public record SignupRequest(String firstname, String lastname, String username, String email, String password) {
}
