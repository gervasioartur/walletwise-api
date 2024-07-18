package com.walletwise.infra.resource.http;

public record SinupRequest(String firstname, String lastname, String username, String email, String password) {
}
