package com.walletwise.infra.gateways.token;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public class ExtractClaim {
    private final ExtractAllClaims extractAllClaims;

    public ExtractClaim(ExtractAllClaims extractAllClaims) {
        this.extractAllClaims = extractAllClaims;
    }

    public <T> T extract(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = this.extractAllClaims.extract(token);
        return claimsResolvers.apply(claims);
    }
}
