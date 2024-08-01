package com.walletwise.infra.persistence.repositories.walletwise;

import com.walletwise.infra.persistence.entities.security.ValidationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IValidationTokenEntityRepository extends JpaRepository<ValidationTokenEntity, UUID> {
    Optional<ValidationTokenEntity> findByTokenAndActive(String token, boolean active);

    Optional<ValidationTokenEntity> findByIdAndActive(UUID id, boolean active);
}
