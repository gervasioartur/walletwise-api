package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.models.Role;
import com.walletwise.infra.persistence.entities.security.RoleEntity;

public class RoleEntityMapper {
    public RoleEntity toRoleEntity(Role roleDomainObject) {
        return RoleEntity.builder().id(roleDomainObject.id()).name(roleDomainObject.name()).build();
    }

    public Role toDomainObject(RoleEntity roleEntity) {
        return new Role(roleEntity.getId(), roleEntity.getName());
    }
}
