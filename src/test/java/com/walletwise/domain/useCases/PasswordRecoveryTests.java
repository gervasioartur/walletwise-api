package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IEmailAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.models.User;
import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.mocks.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class PasswordRecoveryTests {
    private final String baseUrl = Mocks.faker.internet().url();
    private PasswordRecovery passwordRecovery;
    @MockBean
    private IUserAdapter userAdapter;
    @MockBean
    private ICryptoAdapter cryptoAdapter;
    @MockBean
    private IAuthAdapter authAdapter;
    @MockBean
    private IEmailAdapter emailAdapter;

    @BeforeEach
    void setup() {
        this.passwordRecovery = new PasswordRecovery
                (userAdapter, cryptoAdapter, authAdapter, emailAdapter, baseUrl);
    }

    @Test
    @DisplayName("Should stop method execution if user does not exist by email")
    void shouldStopMethodExecutionIfUserDoesNotExistByEmail() {
        String email = Mocks.faker.internet().emailAddress();

        Mockito.when(this.userAdapter.findByEmail(email)).thenReturn(null);

        this.passwordRecovery.recover(email);

        Mockito.verify(this.userAdapter, Mockito.times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should send email to user with instruction to recover password")
    void shouldSendEmailToUserWithInstructionsToRecoverPassword() {
        User savedUser = Mocks.savedUserDomainObjectFactory();

        ValidationToken savedValidationToken = Mocks.validationTokenFactory();

        String token = UUID.randomUUID().toString();
        String hashedToken = savedValidationToken.getToken();

        String resetUrl = this.baseUrl + "/reset-password?token=" + token;
        String message = "Password Reset Request,\n Click the link to reset your password: " + resetUrl;

        Mockito.when(this.userAdapter.findByEmail(savedUser.getEmail())).thenReturn(savedUser);
        Mockito.when(this.cryptoAdapter.generateValidationToken()).thenReturn(token);
        Mockito.when(this.cryptoAdapter.hash(token)).thenReturn(hashedToken);
        Mockito.when(this.authAdapter.saveValidationToken(Mockito.any(ValidationToken.class))).thenReturn(savedValidationToken);
        Mockito.doNothing().when(this.emailAdapter).sendEmail(savedUser.getEmail(), message, "Password Reset Request");

        this.passwordRecovery.recover(savedUser.getEmail());

        Mockito.verify(this.userAdapter, Mockito.times(1)).findByEmail(savedUser.getEmail());
        Mockito.verify(this.cryptoAdapter, Mockito.times(1)).generateValidationToken();
        Mockito.verify(this.cryptoAdapter, Mockito.times(1)).hash(token);
        Mockito.verify(this.authAdapter, Mockito.times(1)).saveValidationToken(Mockito.any(ValidationToken.class));
        Mockito.verify(this.emailAdapter, Mockito.times(1)).sendEmail(savedUser.getEmail(), message, "Password Reset Request");
    }
}
