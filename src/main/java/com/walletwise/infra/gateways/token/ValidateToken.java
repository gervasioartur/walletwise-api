package com.walletwise.infra.gateways.token;


import com.walletwise.infra.gateways.security.SingKey;
import io.jsonwebtoken.Jwts;


public class ValidateToken {
    public void validate(final String token) {
        Jwts.parserBuilder().setSigningKey(new SingKey().getSignKey()).build().parseClaimsJws(token);
    }
}
