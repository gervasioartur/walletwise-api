package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.exceptions.BusinessException;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.entities.models.ValidationToken;

public class ConfirmPasswordRecovery {
    private final IAuthAdapter authAdapter;

    public ConfirmPasswordRecovery(IAuthAdapter authAdapter) {
        this.authAdapter = authAdapter;
    }

    void confirm(String token, String newPassword){
        ValidationToken validationToken = this.authAdapter.findByToken(token);

        if(validationToken == null) throw new NotFoundException("User not found.");

        if(!validationToken.getExpirationDate().isBefore(validationToken.getCreatedAt()))
            throw new BusinessException("Invalid or expired token.");
    }
}
