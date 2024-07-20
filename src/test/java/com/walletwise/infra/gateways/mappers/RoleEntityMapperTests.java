package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.models.Role;
import com.walletwise.infra.persistence.entities.RoleEntity;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleEntityMapperTests {
    private RoleEntityMapper mapper;

    @BeforeEach
    void setup(){
        this.mapper =  new RoleEntityMapper();
    }

    @Test
    @DisplayName("Should return role entity")
    void shouldReturnRoleEntity(){
        Role roleDomainObject = Mocks.savedRoleDomainObjectFactory();

        RoleEntity result = this.mapper.toRoleEntity(roleDomainObject);

        Assertions.assertThat(result.getId()).isEqualTo(roleDomainObject.id());
        Assertions.assertThat(result.getName()).isEqualTo(roleDomainObject.name());
    }

    @Test
    @DisplayName("Should return role domain object")
    void shouldReturnRoleDomainObject(){
        RoleEntity roleEntity =  Mocks.savedRoleEntityFactory();

        Role  result = this.mapper.toDomainObject(roleEntity);

        Assertions.assertThat(result.id()).isEqualTo(roleEntity.getId());
        Assertions.assertThat(result.name()).isEqualTo(roleEntity.getName());
    }
}
