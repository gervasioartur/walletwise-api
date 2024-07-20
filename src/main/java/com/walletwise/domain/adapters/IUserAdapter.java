package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.User;

public interface IUserAdapter {

    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);
}
