package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SigninTests {
    private Signin signin;

    @MockBean
    private IUserAdapter userAdapter;

    @BeforeEach
    void setup() {
        this.signin = new Signin(userAdapter);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException if user does not exist by username ")
    void shouldThrowUnauthorizedExceptionIfUserDoesNotExistsByUsername(){
        String username = Mocks.faker.name().username();
        String password = Mocks.faker.internet().password();

        Mockito.when(this.userAdapter.findByUsername(username)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.signin.signin(username,password));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid username or password.");
        Mockito.verify(this.userAdapter,Mockito.times(1)).findByUsername(username);
    }
}
