package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.security.Role;

public interface IRoleAdapter {
    Role findByName(String name);
}
