package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;

public class Signin {
    private IUserAdapter userAdapter;

    public Signin(IUserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public String signin(String usernameOrEmail, String password) {
        if (usernameOrEmail.contains("@")) {
            if (this.userAdapter.findByEmail(usernameOrEmail) == null)
                throw new UnauthorizedException("Invalid email or password.");
        } else {
            if (this.userAdapter.findByUsername(usernameOrEmail) == null)
                throw new UnauthorizedException("Invalid username or password.");
        }

        return null;
    }
}
