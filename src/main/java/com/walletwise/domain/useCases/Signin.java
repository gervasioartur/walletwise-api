package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.domain.entities.models.User;

public class Signin {
    private IUserAdapter userAdapter;
    private IAuthAdapter authAdapter;

    public Signin(IUserAdapter userAdapter, IAuthAdapter authAdapter) {
        this.userAdapter = userAdapter;
        this.authAdapter = authAdapter;
    }

    public String signin(String usernameOrEmail, String password) {
        User userResult = null;

        if (usernameOrEmail.contains("@")) {
           userResult =  this.userAdapter.findByEmail(usernameOrEmail);
            if (userResult == null) throw new UnauthorizedException("Invalid email or password.");
        } else {
            userResult = this.userAdapter.findByUsername(usernameOrEmail);
            if (userResult == null) throw new UnauthorizedException("Invalid username or password.");
        }

        String result = this.authAdapter.authenticate(userResult.getUsername(),password);
        if(result == null) throw new UnauthorizedException("Bad credentials.");
        return null;
    }
}
