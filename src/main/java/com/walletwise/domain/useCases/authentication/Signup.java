package com.walletwise.domain.useCases.authentication;


import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.ConflictException;
import com.walletwise.domain.entities.models.security.User;

public class Signup {
    private final IUserAdapter userAdapter;
    private final ICryptoAdapter cryptoAdapter;

    public Signup(IUserAdapter userAdapter, ICryptoAdapter cryptoAdapter) {
        this.userAdapter = userAdapter;
        this.cryptoAdapter = cryptoAdapter;
    }

    public void signup(User user) {
        if (this.userAdapter.findByUsername(user.getUsername()) != null)
            throw new ConflictException("Username already exists.");

        if (this.userAdapter.findByEmail(user.getEmail()) != null)
            throw new ConflictException("E-mail already in use.");

        String encodedPassword = this.cryptoAdapter.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.userAdapter.save(user);
    }
}
