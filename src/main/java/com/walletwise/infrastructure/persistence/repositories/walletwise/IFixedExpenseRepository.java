package com.walletwise.infrastructure.persistence.repositories.walletwise;

import com.walletwise.infrastructure.persistence.entities.walletwise.FixedExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IFixedExpenseRepository extends JpaRepository<FixedExpenseEntity, UUID> {
    @Query("SELECT fex FROM FixedExpenseEntity fex WHERE fex.user.id = :userId AND fex.active = true")
    List<FixedExpenseEntity> findByUserId(@Param("userId") UUID userId);
}
