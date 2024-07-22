package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;

public class Signin {
    private IUserAdapter userAdapter;

    public Signin(IUserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public String signin(String username, String password) {
        if(this.userAdapter.findByUsername(username) == null)
            throw new UnauthorizedException("Invalid username or password.");
        return null;
    }
}
