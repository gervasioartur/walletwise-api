package com.walletwise.application.gateways.user;

import com.walletwise.domain.entities.User;

public interface ICreateUserGateway {
    User create(User user);
}