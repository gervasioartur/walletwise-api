package com.walletwise.infrastructure.adapters;

import com.walletwise.domain.adapters.ICryptoAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CryptoAdapter implements ICryptoAdapter {
    private final PasswordEncoder passwordEncoder;

    public CryptoAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public String hash(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }

    @Override
    public String generateValidationToken() {
        return UUID.randomUUID().toString();
    }
}
