package com.walletwise.application.gateways.user;

import com.walletwise.domain.entities.Role;

public interface IFindUserRoleByName {
    Role find(String name);
}
