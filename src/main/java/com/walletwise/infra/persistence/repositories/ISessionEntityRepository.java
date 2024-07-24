package com.walletwise.infra.persistence.repositories;

import com.walletwise.infra.persistence.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
    Optional<SessionEntity> findByTokenAndActive(String token, boolean active);
}
