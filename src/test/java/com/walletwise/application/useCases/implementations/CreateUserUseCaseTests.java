package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.hash.IEncoder;
import com.walletwise.application.gateways.user.IFindUserByEmailGateway;
import com.walletwise.application.gateways.user.IFindUserByUserNameGateway;
import com.walletwise.application.gateways.user.IFindUserRoleByName;
import com.walletwise.application.useCases.contracts.ICreateUserUseCase;
import com.walletwise.domain.entities.User;
import com.walletwise.domain.enums.RoleEnum;
import com.walletwise.domain.exceptions.BusinessException;
import com.walletwise.domain.exceptions.UnexpectedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class CreateUserUseCaseTests {
    private ICreateUserUseCase createUserUseCase;

    @MockBean
    private IFindUserByUserNameGateway findUserByUserNameGateway;
    @MockBean
    private IFindUserByEmailGateway findUserByEmailGateway;
    @MockBean
    private IEncoder encoder;
    @MockBean
    IFindUserRoleByName findUserRoleByName;


    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUserUseCase(
                findUserByUserNameGateway,
                findUserByEmailGateway,
                encoder,
                findUserRoleByName);
    }

    @Test
    @DisplayName("Should throw business exception if username already in use")
    void shouldThrowBusinessExceptionIfUsernameAlreadyInUse() {
        User user = new User("any_fistname", "any_lastname", "any_saved_username", "any_email", "any_password");
        User savedUser = new User("any_saved_fistname", "any_saved_lastname", "any_saved_username", "any_saved_email", "any_saved_password");

        Mockito.when(this.findUserByUserNameGateway.find(user.username())).thenReturn(savedUser);

        Throwable exception = Assertions.catchThrowable(() -> this.createUserUseCase.create(user));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("The username is already in use. Please try another username.");
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(user.username());
    }

    @Test
    @DisplayName("Should throw business exception if email is already in use ")
    void shouldThrowBusinessExceptionIfEmailAlreadyInUse() {
        User user = new User("any_fistname", "any_lastname", "any_username", "any_saved_email", "any_password");
        User savedUser = new User("any_saved_fistname", "any_saved_lastname", "any_saved_username", "any_saved_email", "any_saved_password");

        Mockito.when(this.findUserByUserNameGateway.find(user.username())).thenReturn(null);
        Mockito.when(this.findUserByEmailGateway.find(user.email())).thenReturn(savedUser);

        Throwable exception = Assertions.catchThrowable(() -> this.createUserUseCase.create(user));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("The email is already in use. Please try another email.");
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(user.username());
        Mockito.verify(this.findUserByEmailGateway, Mockito.times(1)).find(user.email());
    }

    @Test
    @DisplayName("Should throws unexpected error exception if user roles not found on the system")
    void shouldThrowsUnexpectedErrorIfUserRolesNotFoundOnSystem() {
        User user = new User("any_fistname", "any_lastname", "any_username", "any_saved_email", "any_password");

        Mockito.when(this.findUserByUserNameGateway.find(user.username())).thenReturn(null);
        Mockito.when(this.findUserByEmailGateway.find(user.email())).thenReturn(null);
        Mockito.when(this.encoder.encode(user.email())).thenReturn(UUID.randomUUID().toString());
        Mockito.when(this.findUserRoleByName.find(RoleEnum.USER.getValue())).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(()-> this.createUserUseCase.create(user));

        Assertions.assertThat(exception).isInstanceOf(UnexpectedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Something went wrong while saving the information. Please concat the administrator.");
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(user.username());
        Mockito.verify(this.findUserByEmailGateway, Mockito.times(1)).find(user.email());
        Mockito.verify(this.encoder, Mockito.times(1)).encode(user.password());
        Mockito.verify(this.findUserRoleByName, Mockito.times(1)).find(RoleEnum.USER.getValue());
    }
}
