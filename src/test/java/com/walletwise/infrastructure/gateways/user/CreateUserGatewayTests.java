package com.walletwise.infrastructure.gateways.user;

import com.walletwise.application.gateways.user.ICreateUserGateway;
import com.walletwise.domain.entities.User;
import com.walletwise.infrastructure.persistence.entities.UserEntity;
import com.walletwise.infrastructure.persistence.repositories.IUserRepository;
import com.walletwise.mocks.UserMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CreateUserGatewayTests {
    private ICreateUserGateway createUserGateway;

    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;

    @BeforeEach
    void setUp() {
        this.createUserGateway = new CreateUserGateway(userRepository, userEntityMapper);
    }

    @Test
    @DisplayName("Should  return user domain object")
    void shouldReturnUserDomainObject() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();
        UserEntity toCreateUserEntity = UserMocks.toCreateUserEntityFactory(toCreateUserDomainObject);

        User createUserDomainObject = UserMocks.createdUserDomainObjectFactory();
        UserEntity createdUserEntity = UserMocks.createdUserEntityFactory(createUserDomainObject);

        Mockito.when(this.userEntityMapper.toUserEntity(toCreateUserDomainObject)).thenReturn(toCreateUserEntity);
        Mockito.when(this.userRepository.save(toCreateUserEntity)).thenReturn(createdUserEntity);
        Mockito.when(this.userEntityMapper.toDomainObject(createdUserEntity)).thenReturn(createUserDomainObject);

        this.createUserGateway.create(toCreateUserDomainObject);

        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toUserEntity(toCreateUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(toCreateUserEntity);
        Mockito.verify(this.userEntityMapper, Mockito.times(1)).toDomainObject(createdUserEntity);
    }
}
