package com.walletwise.infra.persistence.repositories;

import com.walletwise.infra.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByNameAndActive(String name, boolean active);
}
