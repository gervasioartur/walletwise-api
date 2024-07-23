package com.walletwise.infra.persistence.repositories;

import com.walletwise.infra.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsernameAndActive(String username, boolean activate);

    Optional<UserEntity> findByEmailAndActive(String email, boolean activate);

    Optional<UserEntity> findByIdAndActive(UUID id, boolean active);
}
