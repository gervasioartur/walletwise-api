package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.model.Role;

public interface IRoleAdapter {
    Role findByName(String name);
}
