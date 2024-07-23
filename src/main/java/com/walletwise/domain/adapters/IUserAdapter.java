package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.User;

import java.util.UUID;

public interface IUserAdapter {

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(UUID id);

    User save(User user);
}
