package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IEncoderAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderAdapter implements IEncoderAdapter {
    private final PasswordEncoder passwordEncoder;

    public EncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return this.passwordEncoder.encode(password);
    }
}
