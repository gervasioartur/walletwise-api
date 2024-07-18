package com.walletwise.infra.gateways.token;

import org.springframework.security.core.userdetails.UserDetails;

public class IsValidToken {
    private final GetUsernameFromToken getUsernameFromToken;
    private final IsTokenExpired isTokenExpired;

    public IsValidToken(GetUsernameFromToken getUsernameFromToken, IsTokenExpired isTokenExpired) {
        this.getUsernameFromToken = getUsernameFromToken;
        this.isTokenExpired = isTokenExpired;
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken.get(token);
        return (username.equals(userDetails.getUsername()) && !this.isTokenExpired.isTokenExpired(token));
    }
}
