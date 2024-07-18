package com.walletwise.domain.useCases;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.exceptions.ConflictException;
import com.walletwise.domain.entities.model.User;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class SignupTests {
    private Signup signup;

    @MockBean
    private IUserAdapter userAdapter;

    @BeforeEach
    void setup(){
        this.signup =  new Signup(userAdapter);
    }

    @Test
    @DisplayName("Should throw ConflictException if the username already exists")
    void shouldThrowConflictExceptionIfTheUsernameAlreadyExists(){
        User user = Mocks.userWithoutIdFactory();
        User savedUser =  Mocks.userSavedFactory(user);

        Mockito.when(this.userAdapter.findByUsername(user.getUsername()))
                .thenReturn(savedUser);

        Throwable exception = Assertions.catchThrowable(()-> this.signup.signup(user));

        Assertions.assertThat(exception).isInstanceOf(ConflictException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Username already exists.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Should throw ConflictException if email is already in use")
    void shouldThrowConflictExceptionIfEmailIsAlreadyInUse(){
        User user = Mocks.userWithoutIdFactory();
        User savedUser =  Mocks.userSavedFactory(user);

        Mockito.when(this.userAdapter.findByUsername(user.getUsername()))
                .thenReturn(null);
        Mockito.when(this.userAdapter.findByEmail(user.getEmail()))
                .thenReturn(savedUser);

        Throwable exception = Assertions.catchThrowable(()-> this.signup.signup(user));

        Assertions.assertThat(exception).isInstanceOf(ConflictException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Email already in use.");
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(user.getUsername());
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should save user information successfully")
    void shouldSaveUserInformationSuccessfully(){
        User user = Mocks.userWithoutIdFactory();

        Mockito.when(this.userAdapter.findByUsername(user.getUsername()))
                .thenReturn(null);
        Mockito.when(this.userAdapter.findByEmail(user.getEmail()))
                .thenReturn(null);

        this.signup.signup(user);

        Mockito.verify(this.userAdapter, Mockito.times(1)).findByUsername(user.getUsername());
        Mockito.verify(this.userAdapter, Mockito.times(1)).findByEmail(user.getEmail());
        Mockito.verify(this.userAdapter, Mockito.times(1)).save(user);
    }
}
