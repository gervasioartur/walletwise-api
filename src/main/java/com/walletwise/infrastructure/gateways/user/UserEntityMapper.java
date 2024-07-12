package com.walletwise.infrastructure.gateways.user;

import com.walletwise.domain.entities.User;
import com.walletwise.infrastructure.persistence.entities.UserEntity;

public class UserEntityMapper {
    UserEntity toUserEntity(User userDomainObject) {
        return UserEntity
                .builder()
                .firstName(userDomainObject.firstname())
                .lastName(userDomainObject.lastname())
                .username(userDomainObject.username())
                .email(userDomainObject.email())
                .password(userDomainObject.password())
                .build();
    }

    User toDomainObject(UserEntity userEntity) {
        return new User(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword());
    }
}
