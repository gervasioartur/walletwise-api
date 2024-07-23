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
    void setup(){
        this.confirmPasswordRecovery = new ConfirmPasswordRecovery(authAdapter,userAdapter,cryptoAdapter);
    }

    @Test
    @DisplayName("Should throw notFoundException if token does not exist")
    void shouldThrowNotFoundExceptionIfTokenDoesNotExist(){
        String token = UUID.randomUUID().toString();
        String newPassword = Mocks.faker.internet().password();

        Mockito.when(this.authAdapter.findByToken(token)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(()-> this.confirmPasswordRecovery
                .confirm(token,newPassword));

        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User not found.");
        Mockito.verify(this.authAdapter,Mockito.times(1)).findByToken(token);
    }

    @Test
    @DisplayName("Should businessException if token has expired")
    void shouldReturnBusinessExceptionIfTokenHasExpired(){
        String token = UUID.randomUUID().toString();
        String newPassword = Mocks.faker.internet().password();

        ValidationToken savedValidationToken =  Mocks.validationTokenFactory();
        savedValidationToken.setCreatedAt(savedValidationToken.getExpirationDate());

        Mockito.when(this.authAdapter.findByToken(token)).thenReturn(savedValidationToken);

        Throwable exception = Assertions.catchThrowable(()-> this.confirmPasswordRecovery
                .confirm(token,newPassword));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid or expired token.");
        Mockito.verify(this.authAdapter,Mockito.times(1)).findByToken(token);
    }

    @Test
    @DisplayName("Should reset the password")
    void shouldResetThePassword(){
        String token = UUID.randomUUID().toString();
        String newPassword = Mocks.faker.internet().password();
        String encodedNewPassword = Mocks.faker.internet().password();


        ValidationToken savedValidationToken =  Mocks.validationTokenFactory();

        User savedUser = Mocks.savedUserDomainObjectFactory();
        savedUser.setUserId(savedValidationToken.getUserId());
        savedUser.setPassword(encodedNewPassword);

        Mockito.when(this.authAdapter.findByToken(token)).thenReturn(savedValidationToken);
        Mockito.when(this.userAdapter.findById(savedUser.getUserId())).thenReturn(savedUser);
        Mockito.when(this.cryptoAdapter.encode(newPassword)).thenReturn(encodedNewPassword);

        this.confirmPasswordRecovery.confirm(token,newPassword);

        Mockito.verify(this.authAdapter,Mockito.times(1)).findByToken(token);
        Mockito.verify(this.userAdapter,Mockito.times(1)).findById(savedUser.getUserId());
        Mockito.verify(this.cryptoAdapter,Mockito.times(1)).encode(newPassword);
        Mockito.verify(this.userAdapter,Mockito.times(1)).save(savedUser);
    }
}
