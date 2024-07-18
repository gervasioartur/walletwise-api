package com.walletwise.infra.gateways.token;

import com.walletwise.infra.gateways.security.SingKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class ExtractAllClaims {
    private final SingKey singKey;

    public ExtractAllClaims(SingKey singKey) {
        this.singKey = singKey;
    }

    public Claims extract(String token) {
        return Jwts
                .parserBuilder().setSigningKey(singKey.getSignKey()).build().parseClaimsJws(token).getBody();
    }
}
