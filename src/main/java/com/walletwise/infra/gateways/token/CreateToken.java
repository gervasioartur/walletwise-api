package com.walletwise.infra.gateways.token;

import com.walletwise.infra.gateways.security.SignKey;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

public class CreateToken {
    private final SignKey signKey;
//    private final Date expirationDate = Date.from(LocalDateTime.now().plusDays(7L).atZone(ZoneOffset.systemDefault()).toInstant());

    public CreateToken(SignKey signKey) {
        this.signKey = signKey;
    }

    public String create(Map<String, Object> claims, String username) {
        return io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(expirationDate)
                .signWith(signKey.getSignKey(), SignatureAlgorithm.HS256).compact();
    }
}