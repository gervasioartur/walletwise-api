package com.walletwise.infra.persistence.repositories;

import com.walletwise.infra.persistence.entities.FixedExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IFixedExpenseRepository extends JpaRepository<FixedExpenseEntity, UUID> {
}
