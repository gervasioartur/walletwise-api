package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.security.Profile;
import com.walletwise.domain.entities.models.security.Session;
import com.walletwise.domain.entities.models.security.ValidationToken;

import java.util.UUID;

public interface IAuthAdapter {
    String authenticate(String username, String password);

    ValidationToken saveValidationToken(ValidationToken validationToken);

    ValidationToken findValidationTokenByToken(String token);

    void removeValidationToken(UUID id);

    Session saveSession(Session session);

    Profile getUserProfile();

    void closeAllSessions(UUID userId);
}
