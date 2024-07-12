package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.authentication.IAuthenticationGateway;
import com.walletwise.application.gateways.hash.IEncoderGateway;
import com.walletwise.application.gateways.user.ICreateUserGateway;
import com.walletwise.application.gateways.user.IFindUserByEmailGateway;
import com.walletwise.application.gateways.user.IFindUserByUserNameGateway;
import com.walletwise.application.gateways.user.IFindUserRoleByNameGateway;
import com.walletwise.application.useCases.contracts.ICreateUserUseCase;
import com.walletwise.domain.entities.Role;
import com.walletwise.domain.entities.User;
import com.walletwise.domain.entities.UserAccount;
import com.walletwise.domain.enums.RoleEnum;
import com.walletwise.domain.exceptions.BusinessException;
import com.walletwise.domain.exceptions.UnexpectedException;
import com.walletwise.mocks.UserMocks;
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
    @MockBean
    IFindUserRoleByNameGateway findUserRoleByName;
    private ICreateUserUseCase createUserUseCase;
    @MockBean
    private IFindUserByUserNameGateway findUserByUserNameGateway;
    @MockBean
    private IFindUserByEmailGateway findUserByEmailGateway;
    @MockBean
    private IEncoderGateway encoder;
    @MockBean
    private ICreateUserGateway createUserGateway;
    @MockBean
    private IAuthenticationGateway authentication;

    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUserUseCase(
                findUserByUserNameGateway,
                findUserByEmailGateway,
                encoder,
                findUserRoleByName,
                createUserGateway,
                authentication);
    }

    @Test
    @DisplayName("Should throw business exception if username already in use")
    void shouldThrowBusinessExceptionIfUsernameAlreadyInUse() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();
        User createdUserDomainObject = UserMocks.createdUserDomainObjectFactory();

        Mockito.when(this.findUserByUserNameGateway.find(toCreateUserDomainObject.username())).thenReturn(createdUserDomainObject);

        Throwable exception = Assertions.catchThrowable(() -> this.createUserUseCase.create(toCreateUserDomainObject));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("The username is already in use. Please try another username.");
        Assertions.assertThat(toCreateUserDomainObject.username()).isEqualTo(createdUserDomainObject.username());
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(toCreateUserDomainObject.username());
    }

    @Test
    @DisplayName("Should throw business exception if email is already in use ")
    void shouldThrowBusinessExceptionIfEmailAlreadyInUse() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();
        User createdUserDomainObject = UserMocks.createdUserDomainObjectFactory();

        Mockito.when(this.findUserByUserNameGateway.find(toCreateUserDomainObject.username())).thenReturn(null);
        Mockito.when(this.findUserByEmailGateway.find(toCreateUserDomainObject.email())).thenReturn(createdUserDomainObject);

        Throwable exception = Assertions.catchThrowable(() -> this.createUserUseCase.create(toCreateUserDomainObject));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("The email is already in use. Please try another email.");
        Assertions.assertThat(toCreateUserDomainObject.email()).isEqualTo(createdUserDomainObject.email());
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(toCreateUserDomainObject.username());
        Mockito.verify(this.findUserByEmailGateway, Mockito.times(1)).find(toCreateUserDomainObject.email());
    }

    @Test
    @DisplayName("Should throws unexpected error exception if user roles not found on the system")
    void shouldThrowsUnexpectedErrorIfUserRolesNotFoundOnSystem() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();

        Mockito.when(this.findUserByUserNameGateway.find(toCreateUserDomainObject.username())).thenReturn(null);
        Mockito.when(this.findUserByEmailGateway.find(toCreateUserDomainObject.email())).thenReturn(null);
        Mockito.when(this.findUserRoleByName.find(RoleEnum.USER.getValue())).thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.createUserUseCase.create(toCreateUserDomainObject));

        Assertions.assertThat(exception).isInstanceOf(UnexpectedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Something went wrong while saving the information. Please concat the administrator.");
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(toCreateUserDomainObject.username());
        Mockito.verify(this.findUserByEmailGateway, Mockito.times(1)).find(toCreateUserDomainObject.email());
        Mockito.verify(this.findUserRoleByName, Mockito.times(1)).find(RoleEnum.USER.getValue());
    }

    @Test
    @DisplayName("Should  return user account on success")
    void shouldReturnUserAccountOnSuccess() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();
        Role createdRoleDomainObject = UserMocks.createdRoleDomainObjectFactory("USER_ROLE");

        String encodedPassword = UUID.randomUUID().toString();
        String accessToken = UUID.randomUUID().toString();


        Mockito.when(this.findUserByUserNameGateway.find(toCreateUserDomainObject.username())).thenReturn(null);
        Mockito.when(this.findUserByEmailGateway.find(toCreateUserDomainObject.email())).thenReturn(null);
        Mockito.when(this.findUserRoleByName.find(RoleEnum.USER.getValue())).thenReturn(createdRoleDomainObject);
        Mockito.when(this.encoder.encode(toCreateUserDomainObject.password())).thenReturn(encodedPassword);

        User createdUserDomainObject = UserMocks.toCreateUserDomainObjectWithParamsFactory(toCreateUserDomainObject, encodedPassword);

        Mockito.when(this.createUserGateway.create(createdUserDomainObject)).thenReturn(createdUserDomainObject);
        Mockito.when(this.authentication.authenticate(createdUserDomainObject.username(), toCreateUserDomainObject.password())).thenReturn(accessToken);

        UserAccount userAccount = this.createUserUseCase.create(toCreateUserDomainObject);

        Assertions.assertThat(userAccount.accessToken()).isEqualTo(accessToken);

        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(toCreateUserDomainObject.username());
        Mockito.verify(this.findUserByEmailGateway, Mockito.times(1)).find(toCreateUserDomainObject.email());
        Mockito.verify(this.findUserRoleByName, Mockito.times(1)).find(RoleEnum.USER.getValue());
        Mockito.verify(this.encoder, Mockito.times(1)).encode(toCreateUserDomainObject.password());
        Mockito.verify(this.createUserGateway, Mockito.times(1)).create(createdUserDomainObject);
        Mockito.verify(this.authentication, Mockito.times(1)).authenticate(createdUserDomainObject.username(), toCreateUserDomainObject.password());
    }
}
