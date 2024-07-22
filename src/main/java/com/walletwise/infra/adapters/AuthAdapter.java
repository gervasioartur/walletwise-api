package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.infra.gateways.token.GenerateToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthAdapter implements IAuthAdapter {
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;

    public AuthAdapter(AuthenticationManager authenticationManager, GenerateToken generateToken) {
        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
    }

    @Override
    public String authenticate(String username, String password) {
        try {
            Authentication auth = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return this.generateToken.generate(auth.getName());
        }catch (Exception ex){
            return null;
        }
    }
}