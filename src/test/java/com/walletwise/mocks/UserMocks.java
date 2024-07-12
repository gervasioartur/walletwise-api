package com.walletwise.mocks;

import com.walletwise.domain.entities.Role;
import com.walletwise.domain.entities.User;
import com.walletwise.infrastructure.persistence.entities.UserEntity;

import java.util.UUID;

public class UserMocks {
    public static User toCreateUserDomainObjectFactory() {
        return new User("any_fistname", "any_lastname", "any_username", "any_email", "any_password");
    }

    public static User toCreateUserDomainObjectWithParamsFactory(User userDomainObject, String encodedPassword) {
        return new User(userDomainObject.firstname(), userDomainObject.lastname(), userDomainObject.username(), userDomainObject.email(), encodedPassword);
    }

    public static UserEntity toCreateUserEntityFactory(User toCreateUserDomainObject) {
        return UserEntity
                .builder()
                .firstName(toCreateUserDomainObject.firstname())
                .lastName(toCreateUserDomainObject.lastname())
                .username(toCreateUserDomainObject.username())
                .email(toCreateUserDomainObject.email())
                .password(toCreateUserDomainObject.password())
                .build();
    }

    public static UserEntity createdUserEntityFactory(User createdUserDomainObject) {
        return UserEntity
                .builder()
                .firstName(createdUserDomainObject.firstname())
                .lastName(createdUserDomainObject.lastname())
                .username(createdUserDomainObject.username())
                .email(createdUserDomainObject.email())
                .password(createdUserDomainObject.password())
                .active(true)
                .build();
    }

    public static User createdUserDomainObjectFactory() {
        return new User("any_fistname", "any_lastname", "any_username", "any_email", "any_password");
    }

    public static Role createdRoleDomainObjectFactory(String role) {
        return new Role(UUID.randomUUID(), role);
    }
}
