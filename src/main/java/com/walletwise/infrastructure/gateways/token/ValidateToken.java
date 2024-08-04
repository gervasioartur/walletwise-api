package com.walletwise.infrastructure.gateways.token;


import com.walletwise.infrastructure.gateways.security.SignKey;
import io.jsonwebtoken.Jwts;


public class ValidateToken {
    public void validate(final String token) {
        Jwts.parserBuilder().setSigningKey(new SignKey().getSignKey()).build().parseClaimsJws(token);
    }
}
