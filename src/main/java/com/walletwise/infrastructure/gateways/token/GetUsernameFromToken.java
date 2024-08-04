package com.walletwise.infrastructure.gateways.token;

import io.jsonwebtoken.Claims;


public class GetUsernameFromToken {
    private final ExtractClaim extractClaim;

    public GetUsernameFromToken(ExtractClaim extractClaim) {
        this.extractClaim = extractClaim;
    }

    public String get(String token) {
        return this.extractClaim.extract(token, Claims::getSubject);
    }
}
