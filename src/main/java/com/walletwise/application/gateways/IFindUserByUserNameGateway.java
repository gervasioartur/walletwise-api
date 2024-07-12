package com.walletwise.application.gateways;

import com.walletwise.domain.entities.User;

public interface IFindUserByUserNameGateway {
    User find(String userName);
}