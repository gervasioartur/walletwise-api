package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.Role;

public interface IRoleAdapter {
    Role findByName(String name);
}
