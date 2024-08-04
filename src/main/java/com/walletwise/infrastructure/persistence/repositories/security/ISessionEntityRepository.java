package com.walletwise.infrastructure.persistence.repositories.security;

import com.walletwise.infrastructure.persistence.entities.security.SessionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ISessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
    Optional<SessionEntity> findByTokenAndActive(String token, boolean active);

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionEntity session WHERE session.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);
}
