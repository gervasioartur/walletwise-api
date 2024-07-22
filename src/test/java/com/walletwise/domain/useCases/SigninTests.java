package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.domain.entities.models.User;
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
    @MockBean
    private IAuthAdapter authAdapter;

    @BeforeEach
    void setup() {
        this.signin = new Signin(userAdapter,authAdapter);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException if user does not exist by email")
    void shouldThrowUnauthorizedExceptionIfUserDoesNotExistsByEmail() {
        String email = Mocks.faker.internet().emailAddress();
        String password = Mocks.faker.internet().password();

        Mockito.when(this.userAdapter.findByEmail(email)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.signin.signin(email, password));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid email or password.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException if user does not exist by username")
    void shouldThrowUnauthorizedExceptionIfUserDoesNotExistsByUsername() {
        String username = Mocks.faker.name().username();
        String password = Mocks.faker.internet().password();

        Mockito.when(this.userAdapter.findByUsername(username)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.signin.signin(username, password));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid username or password.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException if password is wrong")
    void shouldThrowUnauthorizedExceptionIfPasswordIsWrong() {
        String username = Mocks.faker.name().username();
        String password = Mocks.faker.internet().password();

        User savedUser = Mocks.savedUserDomainObjectFactory();

        Mockito.when(this.userAdapter.findByUsername(username)).thenReturn(savedUser);
        Mockito.when(this.authAdapter.authenticate(username,password)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.signin.signin(username, password));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Bad credentials.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(username);
    }
}
