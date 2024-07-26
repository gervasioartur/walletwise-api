package com.walletwise.infra.gateways.mappers.security;

import com.walletwise.domain.entities.models.Session;
import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.persistence.entities.security.SessionEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;

public class SessionEntityMapper {
    private final UserEntityMapper userEntityMapper;

    public SessionEntityMapper(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    public SessionEntity toSessionEntity(Session session) {
        UserEntity userEntity = this.userEntityMapper.toUserEntity(session.getUser());
        return SessionEntity
                .builder()
                .id(session.getId())
                .token(session.getToken())
                .user(userEntity)
                .active(session.isActive())
                .expirationDate(session.getExpirationDate())
                .createdAt(session.getCreatedAt())
                .build();
    }

    public Session toSessionDomainObject(SessionEntity entity) {
        User user = this.userEntityMapper.toDomainObject(entity.getUser());
        return new Session(entity.getId(),
                entity.getToken(),
                user,
                entity.isActive(),
                entity.getExpirationDate(),
                entity.getCreatedAt());
    }
}
