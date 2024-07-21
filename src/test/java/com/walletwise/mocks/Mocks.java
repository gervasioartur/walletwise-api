package com.walletwise.mocks;

import com.github.javafaker.Faker;
import com.walletwise.application.http.SignupRequest;
import com.walletwise.domain.entities.enums.RoleEnum;
import com.walletwise.domain.entities.models.Role;
import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.persistence.entities.RoleEntity;
import com.walletwise.infra.persistence.entities.UserEntity;

import java.util.UUID;


public class Mocks {
    public static final Faker faker = new Faker();

    public static User userDomainObjectWithoutIdFactory() {
        return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    public static User savedUserDomainObjectFactory(User user) {
        user.setUserId(UUID.randomUUID());
        return user;
    }

    public static User savedUserDomainObjectFactory() {
        return new User(
                UUID.randomUUID(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    public static UserEntity savedUserEntityFactory() {
        return UserEntity
                .builder()
                .id(UUID.randomUUID())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .active(true)
                .build();
    }

    public static UserEntity fromUserToUserEntityFactory(User useDomainObject) {
        return UserEntity
                .builder()
                .id(useDomainObject.getUserId())
                .firstName(useDomainObject.getFirstname())
                .lastName(useDomainObject.getLastname())
                .username(useDomainObject.getUsername())
                .email(useDomainObject.getEmail())
                .password(useDomainObject.getPassword())
                .active(true)
                .build();
    }

    public static User fromUserEntityToUserFactory(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }

    public static User fromSignupRequestToUserFactory(SignupRequest request) {
        return new User(
                request.firstname(),
                request.lastname(),
                request.username(),
                request.email(),
                request.password()
        );
    }

    public static SignupRequest signupRequestToUserFactory() {
        return new SignupRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                "Test@0199");
    }

    public static RoleEntity savedRoleEntityFactory() {
        return RoleEntity
                .builder()
                .id(UUID.randomUUID())
                .name(RoleEnum.ADMIN.getValue())
                .active(true)
                .build();
    }

    public static Role fromRoleEntityToRoleDomainObject(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    public static Role savedRoleDomainObjectFactory() {
        return new Role(UUID.randomUUID(), faker.name().name());
    }
}
