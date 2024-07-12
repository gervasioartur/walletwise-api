package com.walletwise.application.gateways.authentication;

public interface IAuthenticationGateway {
    String authenticate(String username, String password);
}
