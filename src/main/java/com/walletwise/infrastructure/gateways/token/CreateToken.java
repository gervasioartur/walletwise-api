package com.walletwise.infrastructure.gateways.token;

import com.walletwise.infrastructure.gateways.security.SignKey;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class CreateToken {
    private final SignKey signKey;

    public CreateToken(SignKey signKey) {
        this.signKey = signKey;
    }

    public String create(Map<String, Object> claims, String username) {
        return io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(signKey.getSignKey(), SignatureAlgorithm.HS256).compact();
    }
}