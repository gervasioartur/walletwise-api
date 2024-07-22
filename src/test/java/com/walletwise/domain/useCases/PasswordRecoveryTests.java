package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PasswordRecoveryTests {
    private PasswordRecovery passwordRecovery;

    @MockBean
    private IUserAdapter userAdapter;

    @BeforeEach
    void setup(){
        this.passwordRecovery =  new PasswordRecovery(userAdapter);
    }

    @Test
    @DisplayName("Should stop method execution if user does not exist by email")
    void shouldStopMethodExecutionIfUserDoesNotExistByEmail(){
        String email = Mocks.faker.internet().emailAddress();

        Mockito.when(this.userAdapter.findByEmail(email)).thenReturn(null);

        this.passwordRecovery.recover(email);

        Mockito.verify(this.userAdapter, Mockito.times(1)).findByEmail(email);
    }
}
