package com.walletwise.infra.persistence.repositories;

import com.walletwise.infra.persistence.entities.SessionEntity;
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
