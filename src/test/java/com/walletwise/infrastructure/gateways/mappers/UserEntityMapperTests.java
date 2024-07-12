package com.walletwise.infrastructure.gateways.mappers;

import com.walletwise.domain.entities.User;
import com.walletwise.infrastructure.persistence.entities.UserEntity;
import com.walletwise.mocks.UserMocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserEntityMapperTests {
    private final UserEntityMapper mapper = new UserEntityMapper();

    @Test
    @DisplayName("Should return a user entity")
    void shouldReturnUserEntity() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();

        UserEntity toCreateUserEntity = mapper.toUserEntity(toCreateUserDomainObject);

        Assertions.assertThat(toCreateUserEntity.getFirstName()).isEqualTo(toCreateUserDomainObject.firstname());
        Assertions.assertThat(toCreateUserEntity.getLastName()).isEqualTo(toCreateUserDomainObject.lastname());
        Assertions.assertThat(toCreateUserEntity.getUsername()).isEqualTo(toCreateUserDomainObject.username());
        Assertions.assertThat(toCreateUserEntity.getEmail()).isEqualTo(toCreateUserDomainObject.email());
        Assertions.assertThat(toCreateUserEntity.getPassword()).isEqualTo(toCreateUserDomainObject.password());
    }

    @Test
    @DisplayName("Should return user domain object")
    void shouldReturnUserDomainObject() {
        User toCreateUserDomainObject = UserMocks.toCreateUserDomainObjectFactory();
        UserEntity toCreateUserEntity = UserMocks.toCreateUserEntityFactory(toCreateUserDomainObject);

        toCreateUserDomainObject = mapper.toDomainObject(toCreateUserEntity);

        Assertions.assertThat(toCreateUserEntity.getFirstName()).isEqualTo(toCreateUserDomainObject.firstname());
        Assertions.assertThat(toCreateUserEntity.getLastName()).isEqualTo(toCreateUserDomainObject.lastname());
        Assertions.assertThat(toCreateUserEntity.getUsername()).isEqualTo(toCreateUserDomainObject.username());
        Assertions.assertThat(toCreateUserEntity.getEmail()).isEqualTo(toCreateUserDomainObject.email());
        Assertions.assertThat(toCreateUserEntity.getPassword()).isEqualTo(toCreateUserDomainObject.password());
    }
}
