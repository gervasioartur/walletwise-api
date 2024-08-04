package com.walletwise.infrastructure.gateways.token;

import java.util.HashMap;
import java.util.Map;

public class GenerateToken {
    private final CreateToken createToken;

    public GenerateToken(CreateToken createToken) {
        this.createToken = createToken;
    }

    public String generate(String username) {
        Map<String, Object> claims = new HashMap<>();
        return this.createToken.create(claims, username);
    }
}
