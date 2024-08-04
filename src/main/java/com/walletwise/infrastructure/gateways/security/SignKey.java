package com.walletwise.infrastructure.gateways.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

public class SignKey {
    @Value("${app.secret}")
    private String appSecret;

    public Key getSignKey() {
        byte[] keyBytes = Decoders
                .BASE64.decode(this.appSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
