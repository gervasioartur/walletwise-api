package com.walletwise.mocks;

import com.github.javafaker.Faker;
import com.walletwise.domain.entities.model.User;

import java.util.UUID;


public class Mocks {
    private static final Faker faker = new Faker();

    public static User userWithoutIdFactory(){
        return  new User(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    public static User userSavedFactory(User user){
        user.setUserId(UUID.randomUUID());
        return user;
    }
}
