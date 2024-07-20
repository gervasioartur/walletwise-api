package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IPasswordAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordAdapter implements IPasswordAdapter {
    private final PasswordEncoder passwordEncoder;

    public PasswordAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return this.passwordEncoder.encode(password);
    }
}
