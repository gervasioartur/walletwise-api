package com.walletwise.infrastructure.persistence.repositories.security;

import com.walletwise.infrastructure.persistence.entities.security.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPrivilegeRepository extends JpaRepository<PrivilegeEntity, UUID> {
    Optional<PrivilegeEntity> findByNameAndActive(String name, boolean active);
}
