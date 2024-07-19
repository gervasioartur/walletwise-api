package com.walletwise.application.http;

public record SignupRequest(String firstname, String lastname, String username, String email, String password) {
}
