package com.walletwise.infra.persistence.repositories.security;

import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IFixedExpenseRepository extends JpaRepository<FixedExpenseEntity, UUID> {
}
