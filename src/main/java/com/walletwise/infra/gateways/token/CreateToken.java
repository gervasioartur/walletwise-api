package com.walletwise.infra.gateways.token;

import com.walletwise.infra.gateways.security.SingKey;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

public class CreateToken {
    private final SingKey singKey;
    private final Date expirationDate = Date.from(LocalDateTime.now().plusDays(7L).atZone(ZoneOffset.systemDefault()).toInstant());

    public CreateToken(SingKey singKey) {
        this.singKey = singKey;
    }

    public String create(Map<String, Object> claims, String username) {
        return io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(singKey.getSignKey(), SignatureAlgorithm.HS256).compact();
    }
}
