package com.walletwise.application.gateways.user;

import com.walletwise.domain.entities.User;

public interface IFindUserByEmailGateway {
    User find(String userName);
}