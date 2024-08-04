package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.security.User;

import java.util.UUID;

public interface IUserAdapter {

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(UUID id);

    User save(User user);

    void updatePassword(UUID userId, String password);
}
