package com.walletwise.application.gateways.user;

import com.walletwise.domain.entities.Role;

public interface IFindUserRoleByNameGateway {
    Role find(String name);
}
