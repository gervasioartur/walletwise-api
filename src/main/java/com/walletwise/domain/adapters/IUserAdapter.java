package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.model.User;

public interface IUserAdapter {

    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);
}
