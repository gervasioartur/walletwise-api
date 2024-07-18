package com.walletwise.infra.resource.http;

public record SignupRequest(String firstname, String lastname, String username, String email, String password) {
}
