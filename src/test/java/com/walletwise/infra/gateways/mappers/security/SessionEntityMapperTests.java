package com.walletwise.infra.gateways.mappers.security;

import com.walletwise.domain.entities.models.Session;
import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.persistence.entities.security.SessionEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SessionEntityMapperTests {
    private SessionEntityMapper mapper;
    @MockBean
    private UserEntityMapper userEntityMapper;

    @BeforeEach
    void setUp() {
        this.mapper = new SessionEntityMapper(userEntityMapper);
    }

    @Test
    @DisplayName("should return session entity")
    void shouldReturnSessionEntity() {
        Session sessionDomainObject = Mocks.sessionDomainObjectFactory();

        UserEntity userEntity = Mocks.fromUserToUserEntityFactory(sessionDomainObject.getUser());

        Mockito.when(this.userEntityMapper.toUserEntity(sessionDomainObject.getUser())).thenReturn(userEntity);

        SessionEntity result = mapper.toSessionEntity(sessionDomainObject);

        Assertions.assertThat(result.getId()).isEqualTo(sessionDomainObject.getId());
        Assertions.assertThat(result.getToken()).isEqualTo(sessionDomainObject.getToken());
        Assertions.assertThat(result.getUser().getId()).isEqualTo(sessionDomainObject.getUser().getUserId());
        Assertions.assertThat(result.isActive()).isEqualTo(sessionDomainObject.isActive());
        Assertions.assertThat(result.getExpirationDate()).isEqualTo(sessionDomainObject.getExpirationDate());
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(sessionDomainObject.getCreatedAt());
    }

    @Test
    @DisplayName("Should return session domain object")
    void shouldReturnSessionDomainObject() {
        SessionEntity sessionEntity = Mocks.sessionEntityFactory();

        User userDomainObject = Mocks.fromUserEntityToUserFactory(sessionEntity.getUser());

        Mockito.when(this.userEntityMapper.toDomainObject(sessionEntity.getUser())).thenReturn(userDomainObject);

        Session result = mapper.toSessionDomainObject(sessionEntity);

        Assertions.assertThat(result.getId()).isEqualTo(sessionEntity.getId());
        Assertions.assertThat(result.getToken()).isEqualTo(sessionEntity.getToken());
        Assertions.assertThat(result.getUser().getUserId()).isEqualTo(sessionEntity.getUser().getId());
        Assertions.assertThat(result.isActive()).isEqualTo(sessionEntity.isActive());
        Assertions.assertThat(result.getExpirationDate()).isEqualTo(sessionEntity.getExpirationDate());
        Assertions.assertThat(result.getCreatedAt()).isEqualTo(sessionEntity.getCreatedAt());
    }
}
