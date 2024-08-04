package com.walletwise.infrastructure.gateways.token;

import com.walletwise.infrastructure.gateways.security.SignKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class ExtractAllClaims {
    private final SignKey signKey;

    public ExtractAllClaims(SignKey signKey) {
        this.signKey = signKey;
    }

    public Claims extract(String token) {
        return Jwts
                .parserBuilder().setSigningKey(signKey.getSignKey()).build().parseClaimsJws(token).getBody();
    }
}
