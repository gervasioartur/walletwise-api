package com.walletwise.domain.adapters;

import com.walletwise.domain.entities.models.ValidationToken;

public interface IAuthAdapter {
    String authenticate(String username, String password);
    ValidationToken saveValidationToken(ValidationToken validationToken);
}
