package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.model.Role;
import com.walletwise.infra.persistence.entities.RoleEntity;

public class RoleEntityMapper {
    public RoleEntity toRoleEntity(Role roleDomainObject) {
        return RoleEntity.builder().name(roleDomainObject.name()).build();
    }

    public Role toDomainObject(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(), roleEntity.getName());
    }
}
