package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IEmailAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.models.User;
import com.walletwise.domain.entities.models.ValidationToken;

import java.time.LocalDateTime;

public class PasswordRecovery {
    private final IUserAdapter userAdapter;
    private final ICryptoAdapter cryptoAdapter;
    private final IAuthAdapter authAdapter;
    private final IEmailAdapter emailAdapter;
    private final String baseUrl;

    public PasswordRecovery(IUserAdapter userAdapter,
                            ICryptoAdapter cryptoAdapter,
                            IAuthAdapter authAdapter,
                            IEmailAdapter emailAdapter,
                            String baseUrl) {
        this.userAdapter = userAdapter;
        this.cryptoAdapter = cryptoAdapter;
        this.authAdapter = authAdapter;
        this.emailAdapter = emailAdapter;
        this.baseUrl = baseUrl;
    }

    public void recover(String email) {
        User userResult = this.userAdapter.findByEmail(email);
        if (userResult == null) return;

        String token = this.cryptoAdapter.generateValidationToken();
        String hashedToken = this.cryptoAdapter.hash(token);

        ValidationToken validationToken = new ValidationToken(userResult.getUserId(), hashedToken, LocalDateTime.now().plusHours(1));
        this.authAdapter.saveValidationToken(validationToken);

        String resetUrl = this.baseUrl + "/reset-password?token=" + token;
        String message = "Password Reset Request,\n Click the link to reset your password: " + resetUrl;
        this.emailAdapter.sendEmail(userResult.getEmail(), message, "Password Reset Request");
    }
}
