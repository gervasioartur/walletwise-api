package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.UnauthorizedException;
import com.walletwise.domain.entities.models.Session;
import com.walletwise.domain.entities.models.User;
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
class SigninTests {
    private Signin signin;

    @MockBean
    private IUserAdapter userAdapter;
    @MockBean
    private IAuthAdapter authAdapter;


    @BeforeEach
    void setup() {
        this.signin = new Signin(userAdapter, authAdapter);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException if user does not exist by email")
    void shouldThrowUnauthorizedExceptionIfUserDoesNotExistsByEmail() {
        String email = Mocks.faker.internet().emailAddress();
        String password = Mocks.faker.internet().password();

        Mockito.when(this.userAdapter.findByEmail(email)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.signin.signin(email, password));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid username/email or password.");
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
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid username/email or password.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException if password is wrong")
    void shouldThrowUnauthorizedExceptionIfPasswordIsWrong() {
        User savedUser = Mocks.savedUserDomainObjectFactory();

        String username = savedUser.getUsername();
        String password = Mocks.faker.internet().password();


        Mockito.when(this.userAdapter.findByUsername(username)).thenReturn(savedUser);
        Mockito.when(this.authAdapter.authenticate(username, password)).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.signin.signin(username, password));

        Assertions.assertThat(exception).isInstanceOf(UnauthorizedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Invalid username/email or password.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(username);
        Mockito.verify(this.authAdapter, Mockito.times(1)).authenticate(username, password);
    }

    @Test
    @DisplayName("Should return access token on sign in success")
    void shouldReturnAccessTokenOnSignInSuccess() {
        User savedUser = Mocks.savedUserDomainObjectFactory();

        String username = savedUser.getUsername();
        String password = Mocks.faker.internet().password();

        String accessToken = UUID.randomUUID().toString();

        Session toSaveSession = Mocks.sessionWithOutIdDomainObjectFactory();
        toSaveSession.setUser(savedUser);
        toSaveSession.setToken(accessToken);

        Session savedSession = Mocks.sessionDomainObjectFactory(toSaveSession);

        Mockito.when(this.userAdapter.findByUsername(username)).thenReturn(savedUser);
        Mockito.when(this.authAdapter.authenticate(username, password)).thenReturn(accessToken);
        Mockito.when(this.authAdapter.saveSession(Mockito.any(Session.class))).thenReturn(savedSession);

        String result = this.signin.signin(username, password);

        Assertions.assertThat(result).isEqualTo(accessToken);
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(username);
        Mockito.verify(this.authAdapter, Mockito.times(1)).authenticate(username, password);
        Mockito.verify(this.authAdapter, Mockito.times(1)).saveSession(Mockito.any(Session.class));
    }
}
