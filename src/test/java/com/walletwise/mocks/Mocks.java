package com.walletwise.mocks;

import com.github.javafaker.Faker;
import com.walletwise.application.http.SignupRequest;
import com.walletwise.domain.entities.enums.GeneralEnumText;
import com.walletwise.domain.entities.enums.RoleEnum;
import com.walletwise.domain.entities.models.*;
import com.walletwise.infra.persistence.entities.RoleEntity;
import com.walletwise.infra.persistence.entities.SessionEntity;
import com.walletwise.infra.persistence.entities.UserEntity;
import com.walletwise.infra.persistence.entities.ValidationTokenEntity;

import java.time.LocalDateTime;
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

    public static ValidationToken validationTokenWithOutIdFactory() {
        return new ValidationToken
                (UUID.randomUUID(), UUID.randomUUID().toString(), LocalDateTime.now().plusHours(1));
    }

    public static ValidationToken validationTokenFactory(ValidationTokenEntity entity) {
        return new ValidationToken(entity.getId(),
                entity.getUser().getId(),
                entity.getToken(),
                entity.getExpirationDate(),
                entity.getCreatedAt());
    }

    public static ValidationToken validationTokenFactory() {
        LocalDateTime now = LocalDateTime.now();
        return new ValidationToken(UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                now.plusHours(1),
                now);
    }

    public static ValidationTokenEntity validationTokenEntityFactory() {
        LocalDateTime now = LocalDateTime.now();
        return ValidationTokenEntity
                .builder()
                .id(UUID.randomUUID())
                .user(UserEntity.builder().id(UUID.randomUUID()).build())
                .token(UUID.randomUUID().toString())
                .expirationDate(now.plusHours(1))
                .createdAt(now)
                .build();
    }

    public static Session sessionWithOutIdDomainObjectFactory() {
        LocalDateTime now = LocalDateTime.now();
        return new Session(UUID.randomUUID().toString(),
                Mocks.savedUserDomainObjectFactory(),
                true,
                now.plusHours(2),
                now);
    }

    public static Session sessionDomainObjectFactory() {
        LocalDateTime now = LocalDateTime.now();
        return new Session(UUID.randomUUID(),
                UUID.randomUUID().toString(),
                Mocks.savedUserDomainObjectFactory(),
                true,
                now.plusHours(2),
                now);
    }

    public static Session sessionDomainObjectFactory(Session session) {
        return new Session(UUID.randomUUID(),
                session.getToken(),
                session.getUser(),
                session.isActive(),
                session.getExpirationDate(),
                session.getCreatedAt());
    }

    public static SessionEntity formSessionToSessionEntityFactory(Session session) {
        UserEntity userEntity = Mocks.fromUserToUserEntityFactory(session.getUser());
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

    public static Session formSessionEntityToSessionFactory(SessionEntity session) {
        User user = Mocks.fromUserEntityToUserFactory(session.getUser());
        return new Session(session.getId(),
                session.getToken(),
                user,
                true,
                session.getExpirationDate(),
                session.getCreatedAt());
    }

    public static SessionEntity sessionEntityFactory(SessionEntity session) {
        return SessionEntity
                .builder()
                .id(UUID.randomUUID())
                .token(session.getToken())
                .user(session.getUser())
                .active(session.isActive())
                .expirationDate(session.getExpirationDate())
                .createdAt(session.getCreatedAt())
                .build();
    }


    public static SessionEntity sessionEntityFactory() {
        UserEntity userEntity = Mocks.savedUserEntityFactory();
        LocalDateTime now = LocalDateTime.now();

        return SessionEntity
                .builder()
                .id(UUID.randomUUID())
                .token(UUID.randomUUID().toString())
                .user(userEntity)
                .active(true)
                .expirationDate(now.plusHours(1))
                .createdAt(now)
                .build();
    }

    public static Profile profileFactory() {
        return new Profile(
                UUID.randomUUID(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().image(),
                GeneralEnumText.LIGHT_THEME.getValue());
    }
}
