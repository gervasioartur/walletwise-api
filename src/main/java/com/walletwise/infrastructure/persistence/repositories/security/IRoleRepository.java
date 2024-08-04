package com.walletwise.infrastructure.persistence.repositories.security;

import com.walletwise.infrastructure.persistence.entities.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByNameAndActive(String name, boolean active);
}
