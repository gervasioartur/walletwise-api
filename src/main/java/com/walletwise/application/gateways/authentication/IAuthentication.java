package com.walletwise.application.gateways.authentication;

public interface IAuthentication {
    String authenticate(String username, String password);
}
