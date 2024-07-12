package com.walletwise.application.gateways;

import com.walletwise.domain.entities.User;

public interface ICreateUserGateway {
    User create(User user);
}
