package com.walletwise.infrastructure.gateways.mappers.security;

import com.walletwise.domain.entities.models.security.Role;
import com.walletwise.infrastructure.persistence.entities.security.RoleEntity;

public class RoleEntityMapper {
    public RoleEntity toRoleEntity(Role roleDomainObject) {
        return RoleEntity.builder().id(roleDomainObject.id()).name(roleDomainObject.name()).build();
    }

    public Role toDomainObject(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(), roleEntity.getName());
    }
}
