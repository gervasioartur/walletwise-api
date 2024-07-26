package com.walletwise.domain.useCases.auth;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.enums.GeneralEnumInt;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.domain.entities.models.Session;
import com.walletwise.domain.entities.models.User;

import java.time.LocalDateTime;

public class Signin {
    private final IUserAdapter userAdapter;
    private final IAuthAdapter authAdapter;

    public Signin(IUserAdapter userAdapter, IAuthAdapter authAdapter) {
        this.userAdapter = userAdapter;
        this.authAdapter = authAdapter;
    }

    public String signin(String usernameOrEmail, String password) {
        User userResult = usernameOrEmail.contains("@")
                ? userAdapter.findByEmail(usernameOrEmail)
                : userAdapter.findByUsername(usernameOrEmail);

        if (userResult == null) throw new UnauthorizedException("Invalid username/email or password.");

        String result = this.authAdapter.authenticate(userResult.getUsername(), password);
        if (result == null) throw new UnauthorizedException("Invalid username/email or password.");

        Session session = new Session(result,
                userResult,
                true,
                LocalDateTime.now().plusHours(GeneralEnumInt.JWT_TOKEN_EXPIRATION.getValue()));

        session = this.authAdapter.saveSession(session);
        return session.getToken();
    }
}
