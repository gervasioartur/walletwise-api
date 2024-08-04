package com.walletwise.domain.useCases.auth;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.exceptions.ForbiddenException;
import com.walletwise.domain.entities.models.security.Profile;

public class GetUserProfile {
    private final IAuthAdapter authAdapter;

    public GetUserProfile(IAuthAdapter authAdapter) {
        this.authAdapter = authAdapter;
    }

    public Profile getUserProfile() {
        Profile profile = this.authAdapter.getUserProfile();
        if (profile == null) throw new ForbiddenException("Forbidden.");
        return profile;
    }
}
