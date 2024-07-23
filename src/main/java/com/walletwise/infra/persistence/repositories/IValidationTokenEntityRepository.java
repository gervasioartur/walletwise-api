package com.walletwise.infra.persistence.repositories;

import com.walletwise.infra.persistence.entities.ValidationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IValidationTokenEntityRepository extends JpaRepository<ValidationTokenEntity, UUID> {
}
