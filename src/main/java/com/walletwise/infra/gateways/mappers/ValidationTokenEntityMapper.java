package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.infra.persistence.entities.ValidationTokenEntity;

public class ValidationTokenEntityMapper {
    public ValidationTokenEntity toValidationTokenEntity(ValidationToken validationToken) {
        return ValidationTokenEntity
                .builder()
                .id(validationToken.getId())
                .token(validationToken.getToken())
                .expirationDate(validationToken.getExpirationDate())
                .build();
    }

    public ValidationToken toValidationTokenDomainObject(ValidationTokenEntity entity) {
        return new ValidationToken(entity.getId(), entity.getToken(), entity.getExpirationDate());
    }
}
