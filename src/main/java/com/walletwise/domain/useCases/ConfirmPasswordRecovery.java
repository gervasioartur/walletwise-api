package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.BusinessException;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.entities.models.User;
import com.walletwise.domain.entities.models.ValidationToken;

public class ConfirmPasswordRecovery {
    private final IAuthAdapter authAdapter;
    private final IUserAdapter userAdapter;
    private final ICryptoAdapter cryptoAdapter;

    public ConfirmPasswordRecovery(IAuthAdapter authAdapter,
                                   IUserAdapter userAdapter,
                                   ICryptoAdapter cryptoAdapter) {
        this.authAdapter = authAdapter;
        this.userAdapter = userAdapter;
        this.cryptoAdapter = cryptoAdapter;
    }

    public void confirm(String token, String newPassword) {
        String encodedToken = this.cryptoAdapter.hash(token);
        ValidationToken validationToken = this.authAdapter.findValidationTokenByToken(encodedToken);

        if (validationToken == null) throw new NotFoundException("User not found.");

        if (!validationToken.getCreatedAt().isBefore(validationToken.getExpirationDate()))
            throw new BusinessException("Invalid or expired token.");

        User user = this.userAdapter.findById(validationToken.getUserId());
        String encodeNewPassword = this.cryptoAdapter.encode(newPassword);
        user.setPassword(encodeNewPassword);
        this.userAdapter.updatePassword(user.getUserId(), user.getPassword());

        validationToken.setActive(false);
        this.authAdapter.removeValidationToken(validationToken.getId());
    }
}
