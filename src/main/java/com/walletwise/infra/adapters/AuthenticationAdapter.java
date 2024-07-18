package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IAuthenticationAdapter;
import com.walletwise.infra.gateways.token.GenerateToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationAdapter implements IAuthenticationAdapter {
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;

    public AuthenticationAdapter(AuthenticationManager authenticationManager, GenerateToken generateToken) {
        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
    }

    @Override
    public String authenticate(String username, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return this.generateToken.generate(username);
    }
}
