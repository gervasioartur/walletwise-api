package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.model.User;

public interface IUserAdapter {
    User save(User user);

    User findByUsername(String username);

    User findByEmail(String email);
}
