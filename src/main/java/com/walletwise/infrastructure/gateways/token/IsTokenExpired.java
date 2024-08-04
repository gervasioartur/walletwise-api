package com.walletwise.infrastructure.gateways.token;

import io.jsonwebtoken.Claims;

import java.util.Date;

public class IsTokenExpired {
    private final ExtractClaim extractClaim;

    public IsTokenExpired(ExtractClaim extractClaim) {
        this.extractClaim = extractClaim;
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim.extract(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}
