package com.walletwise.infrastructure.gateways.mappers.security;

import com.walletwise.domain.entities.models.security.Profile;
import com.walletwise.domain.entities.models.security.User;
import com.walletwise.infrastructure.persistence.entities.security.UserEntity;
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


    @Test
    @DisplayName("Should return profile")
    void shouldReturnProfile() {
        UserEntity userEntity = Mocks.savedUserEntityFactory();

        Profile result = this.mapper.toProfile(userEntity);

        Assertions.assertThat(result.getUserId()).isEqualTo(userEntity.getId());
        Assertions.assertThat(result.getFirstname()).isEqualTo(userEntity.getFirstName());
        Assertions.assertThat(result.getLastname()).isEqualTo(userEntity.getLastName());
        Assertions.assertThat(result.getUsername()).isEqualTo(userEntity.getUsername());
        Assertions.assertThat(result.getEmail()).isEqualTo(userEntity.getEmail());
        Assertions.assertThat(result.getTheme()).isEqualTo(userEntity.getTheme());
    }
}
