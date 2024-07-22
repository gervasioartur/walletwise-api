package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IUserAdapter;

public class PasswordRecovery {
    private IUserAdapter userAdapter;

    public PasswordRecovery(IUserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void recover(String email){
        if(this.userAdapter.findByEmail(email) == null) return;
    }
}
