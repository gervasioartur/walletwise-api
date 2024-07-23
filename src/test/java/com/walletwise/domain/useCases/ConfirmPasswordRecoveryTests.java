package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.exceptions.NotFoundException;
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

    @BeforeEach
    void setup(){
        this.confirmPasswordRecovery = new ConfirmPasswordRecovery(authAdapter);
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
}
