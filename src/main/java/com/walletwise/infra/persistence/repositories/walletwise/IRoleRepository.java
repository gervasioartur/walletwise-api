package com.walletwise.infra.persistence.repositories.walletwise;

import com.walletwise.infra.persistence.entities.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByNameAndActive(String name, boolean active);
}
