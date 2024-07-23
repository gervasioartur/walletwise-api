package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.BusinessException;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.entities.models.User;
import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class ConfirmPasswordRecoveryTests {
    private ConfirmPasswordRecovery confirmPasswordRecovery;

    @MockBean
    private IAuthAdapter authAdapter;
    @MockBean
    private IUserAdapter userAdapter;
    @MockBean
    private ICryptoAdapter cryptoAdapter;

    @BeforeEach
    void setup() {
        this.confirmPasswordRecovery = new ConfirmPasswordRecovery(authAdapter, userAdapter, cryptoAdapter);
    }

    @Test
    @DisplayName("Should throw notFoundException if token does not exist")
    void shouldThrowNotFoundExceptionIfTokenDoesNotExist() {
        String token = Mocks.faker.lorem().word();
        String hashedToken = UUID.randomUUID().toString();

        String newPassword = Mocks.faker.internet().password();

        Mockito.when(this.cryptoAdapter.hash(token)).thenReturn(hashedToken);
        Mockito.when(this.authAdapter.findValidationTokenByToken(hashedToken)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.confirmPasswordRecovery
                .confirm(token, newPassword));

        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User not found.");
        Mockito.verify(this.cryptoAdapter, Mockito.times(1)).hash(token);
        Mockito.verify(this.authAdapter, Mockito.times(1)).findValidationTokenByToken(hashedToken);
    }

    @Test
    @DisplayName("Should businessException if token has expired")
    void shouldReturnBusinessExceptionIfTokenHasExpired() {
        String token = Mocks.faker.lorem().word();
        String hashedToken = UUID.randomUUID().toString();

        String newPassword = Mocks.faker.internet().password();

        ValidationToken savedValidationToken = Mocks.validationTokenFactory();
        savedValidationToken.setToken(hashedToken);
        savedValidationToken.setCreatedAt(savedValidationToken.getExpirationDate());

        Mockito.when(this.cryptoAdapter.hash(token)).thenReturn(hashedToken);
        Mockito.when(this.authAdapter.findValidationTokenByToken(hashedToken)).thenReturn(savedValidationToken);

        Throwable exception = Assertions.catchThrowable(() -> this.confirmPasswordRecovery
                .confirm(token, newPassword));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid or expired token.");
        Mockito.verify(this.cryptoAdapter, Mockito.times(1)).hash(token);
        Mockito.verify(this.authAdapter, Mockito.times(1)).findValidationTokenByToken(hashedToken);
    }

    @Test
    @DisplayName("Should reset the password")
    void shouldResetThePassword() {
        String token = Mocks.faker.lorem().word();
        String hashedToken = UUID.randomUUID().toString();

        String newPassword = Mocks.faker.internet().password();

        String encodedNewPassword = Mocks.faker.internet().password();

        ValidationToken savedValidationToken = Mocks.validationTokenFactory();
        savedValidationToken.setToken(hashedToken);


        User savedUser = Mocks.savedUserDomainObjectFactory();
        savedUser.setUserId(savedValidationToken.getUserId());
        savedUser.setPassword(encodedNewPassword);

        Mockito.when(this.cryptoAdapter.hash(token)).thenReturn(hashedToken);
        Mockito.when(this.authAdapter.findValidationTokenByToken(hashedToken)).thenReturn(savedValidationToken);
        Mockito.when(this.userAdapter.findById(savedUser.getUserId())).thenReturn(savedUser);
        Mockito.when(this.cryptoAdapter.encode(newPassword)).thenReturn(encodedNewPassword);
        savedValidationToken.setActive(true);

        this.confirmPasswordRecovery.confirm(token, newPassword);

        Mockito.verify(this.cryptoAdapter, Mockito.times(1)).hash(token);
        Mockito.verify(this.authAdapter, Mockito.times(1)).findValidationTokenByToken(hashedToken);
        Mockito.verify(this.userAdapter, Mockito.times(1)).findById(savedUser.getUserId());
        Mockito.verify(this.cryptoAdapter, Mockito.times(1)).encode(newPassword);
        Mockito.verify(this.userAdapter, Mockito.times(1)).save(savedUser);
        Mockito.verify(this.authAdapter, Mockito.times(1))
                .removeValidationToken(savedValidationToken.getId());

    }
}
