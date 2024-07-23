package com.walletwise.domain.adapters;

public interface ICryptoAdapter {
    String encode(String password);

    String generateValidationToken();
}
