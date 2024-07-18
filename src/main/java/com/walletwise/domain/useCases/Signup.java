package com.walletwise.domain.useCases;


import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.ConflictException;
import com.walletwise.domain.entities.model.User;

public class Signup {
    private final IUserAdapter userAdapter;

    public Signup(IUserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void  signup(User user){
        if(this.userAdapter.findByUsername(user.getUsername()) != null)
            throw new ConflictException("Username already exists.");
    }
}
