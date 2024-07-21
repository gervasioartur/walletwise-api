package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.persistence.entities.UserEntity;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserEntityMapperTests {
    private UserEntityMapper mapper;

    @BeforeEach
    void setup() {
        this.mapper = new UserEntityMapper();
    }

    @Test
    @DisplayName("Should return user entity")
    void shouldReturnUserEntity() {
        User userDomainObject = Mocks.savedUserDomainObjectFactory();

        UserEntity result = this.mapper.toUserEntity(userDomainObject);

        Assertions.assertThat(result.getId()).isEqualTo(userDomainObject.getUserId());
        Assertions.assertThat(result.getFirstName()).isEqualTo(userDomainObject.getFirstname());
        Assertions.assertThat(result.getLastName()).isEqualTo(userDomainObject.getLastname());
        Assertions.assertThat(result.getUsername()).isEqualTo(userDomainObject.getUsername());
        Assertions.assertThat(result.getEmail()).isEqualTo(userDomainObject.getEmail());
        Assertions.assertThat(result.getPassword()).isEqualTo(userDomainObject.getPassword());
    }

    @Test
    @DisplayName("Should return user domain object")
    void shouldReturnUserDomainObject() {
        UserEntity userEntity = Mocks.savedUserEntityFactory();

        User result = this.mapper.toDomainObject(userEntity);

        Assertions.assertThat(result.getUserId()).isEqualTo(userEntity.getId());
        Assertions.assertThat(result.getFirstname()).isEqualTo(userEntity.getFirstName());
        Assertions.assertThat(result.getLastname()).isEqualTo(userEntity.getLastName());
        Assertions.assertThat(result.getUsername()).isEqualTo(userEntity.getUsername());
        Assertions.assertThat(result.getEmail()).isEqualTo(userEntity.getEmail());
        Assertions.assertThat(result.getPassword()).isEqualTo(userEntity.getPassword());
    }
}
