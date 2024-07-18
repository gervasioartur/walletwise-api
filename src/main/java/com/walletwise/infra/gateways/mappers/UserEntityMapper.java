package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.model.User;
import com.walletwise.infra.persistence.entities.UserEntity;

public class UserEntityMapper {
    public UserEntity toUserEntity(User userDomainObject) {
        return UserEntity
                .builder()
                .id(userDomainObject.getUserId())
                .firstName(userDomainObject.getFirstname())
                .lastName(userDomainObject.getLastname())
                .username(userDomainObject.getUsername())
                .email(userDomainObject.getEmail())
                .password(userDomainObject.getPassword())
                .build();
    }

    public User toDomainObject(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword());
    }
}
