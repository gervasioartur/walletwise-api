package com.walletwise.domain.adapters;

public interface IAuthenticationAdapter {
    String authenticate(String username, String password);
}
