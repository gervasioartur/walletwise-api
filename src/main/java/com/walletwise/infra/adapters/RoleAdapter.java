package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IRoleAdapter;
import com.walletwise.domain.entities.models.Role;
import com.walletwise.infra.gateways.mappers.RoleEntityMapper;
import com.walletwise.infra.persistence.entities.security.RoleEntity;
import com.walletwise.infra.persistence.repositories.walletwise.IRoleRepository;

import java.util.Optional;

public class RoleAdapter implements IRoleAdapter {
    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    public RoleAdapter(IRoleRepository roleRepository, RoleEntityMapper roleEntityMapper) {
        this.roleRepository = roleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public Role findByName(String name) {
        Optional<RoleEntity> roleEntity = this.roleRepository.findByNameAndActive(name, true);
        return roleEntity.map(this.roleEntityMapper::toDomainObject).orElse(null);
    }
}
