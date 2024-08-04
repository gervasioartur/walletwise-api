package com.walletwise.infrastructure.gateways.mappers.security;

import com.walletwise.domain.entities.models.security.ValidationToken;
import com.walletwise.infrastructure.persistence.entities.security.UserEntity;
import com.walletwise.infrastructure.persistence.entities.security.ValidationTokenEntity;

public class ValidationTokenEntityMapper {
    public ValidationTokenEntity toValidationTokenEntity(ValidationToken validationToken) {
        return ValidationTokenEntity
                .builder()
                .id(validationToken.getId())
                .user(UserEntity.builder().id(validationToken.getUserId()).build())
                .token(validationToken.getToken())
                .expirationDate(validationToken.getExpirationDate())
                .createdAt(validationToken.getCreatedAt())
                .build();
    }

    public ValidationToken toValidationTokenDomainObject(ValidationTokenEntity entity) {
        return new ValidationToken(entity.getId(),
                entity.getUser().getId(),
                entity.getToken(),
                entity.getExpirationDate(),
                entity.getCreatedAt());
    }
}
