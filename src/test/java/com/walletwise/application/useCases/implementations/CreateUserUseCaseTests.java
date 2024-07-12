package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.IFindUserByUserNameGateway;
import com.walletwise.application.useCases.contracts.ICreateUserUseCase;
import com.walletwise.domain.entities.User;
import com.walletwise.domain.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CreateUserUseCaseTests {
    private ICreateUserUseCase createUserUseCase;

    @MockBean
    private IFindUserByUserNameGateway findUserByUserNameGateway;

    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUserUseCase(findUserByUserNameGateway);
    }

    @Test
    @DisplayName("Should throw business exception if username already taken")
    void shouldThrowBusinessExceptionIfUsernameAlreadyTaken() {
        User user = new User("any_fistname", "any_lastname", "any_saved_username", "any_email", "any_password");
        User savedUser = new User("any_saved_fistname", "any_saved_lastname", "any_saved_username", "any_saved_email", "any_saved_password");

        Mockito.when(this.findUserByUserNameGateway.find(user.username())).thenReturn(savedUser);

        Throwable exception = Assertions.catchThrowable(() -> this.createUserUseCase.create(user));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("The username is already in use. Please try another username.");
        Mockito.verify(this.findUserByUserNameGateway, Mockito.times(1)).find(user.username());
    }
}
