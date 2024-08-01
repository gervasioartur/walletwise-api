package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IRoleAdapter;
import com.walletwise.domain.entities.models.Role;
import com.walletwise.infra.gateways.mappers.security.RoleEntityMapper;
import com.walletwise.infra.persistence.entities.security.RoleEntity;
import com.walletwise.infra.persistence.repositories.walletwise.IRoleRepository;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class RoleAdapterTests {
    private IRoleAdapter roleAdapter;
    @MockBean
    private IRoleRepository roleRepository;
    @MockBean
    private RoleEntityMapper mapper;

    @BeforeEach
    void setup() {
        this.roleAdapter = new RoleAdapter(roleRepository, mapper);
    }

    @Test
    @DisplayName("Should return null if role does not exist on find by name")
    void shouldReturnNullIfRoleDoesNotExistByName() {
        String name = "any_name";

        Mockito.when(this.roleRepository.findByNameAndActive(name, true)).thenReturn(Optional.empty());

        Role result = this.roleAdapter.findByName(name);

        Assertions.assertThat(result).isNull();
        Mockito.verify(this.roleRepository, Mockito.times(1)).findByNameAndActive(name, true);
    }

    @Test
    @DisplayName("Should return Role domain object on find by name")
    void shouldReturnRoleDomainObjectOnFindByName() {
        RoleEntity savedRoleEntity = Mocks.savedRoleEntityFactory();
        Role roleDomainObject = Mocks.fromRoleEntityToRoleDomainObject(savedRoleEntity);

        Mockito.when(this.roleRepository.findByNameAndActive(savedRoleEntity.getName(), true))
                .thenReturn(Optional.of(savedRoleEntity));
        Mockito.when(this.mapper.toDomainObject(savedRoleEntity)).thenReturn(roleDomainObject);

        Role result = this.roleAdapter.findByName(savedRoleEntity.getName());

        Assertions.assertThat(result).isEqualTo(roleDomainObject);
        Mockito.verify(this.roleRepository, Mockito.times(1))
                .findByNameAndActive(savedRoleEntity.getName(), true);
    }
}
