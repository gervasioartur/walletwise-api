package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.exceptions.NotFoundException;

public class ConfirmPasswordRecovery {
    private final IAuthAdapter authAdapter;

    public ConfirmPasswordRecovery(IAuthAdapter authAdapter) {
        this.authAdapter = authAdapter;
    }

    void confirm(String token, String newPassword){
        if(this.authAdapter.findByToken(token) == null) throw new NotFoundException("User not found.");
    }
}
