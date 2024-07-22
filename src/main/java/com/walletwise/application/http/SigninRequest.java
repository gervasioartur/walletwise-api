package com.walletwise.application.http;

public record SigninRequest(String usernameOrEmail, String password) {
}
