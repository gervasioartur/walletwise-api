package com.walletwise.domain.adapters;

import java.util.UUID;

public interface ICryptoAdapter {
    String encode(String password);
    String generateToken();
}
