package com.walletwise.mocks;

import com.github.javafaker.Faker;
import com.walletwise.domain.entities.model.User;
import com.walletwise.infra.persistence.entities.UserEntity;

import java.util.UUID;


public class Mocks {
    private static final Faker faker = new Faker();

    public static User userWithoutIdFactory() {
        return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    public static User savedUserFactory(User user) {
        user.setUserId(UUID.randomUUID());
        return user;
    }

    public static UserEntity fromUserToUserEntity(User useDomainObject) {
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

    public static User fromUserEntityToUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }
}
