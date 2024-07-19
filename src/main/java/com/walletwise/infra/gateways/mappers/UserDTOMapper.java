package com.walletwise.infra.gateways.mappers;

import com.walletwise.application.http.SignupRequest;
import com.walletwise.domain.entities.model.User;

public class UserDTOMapper {
    public User toUserDomainObject(SignupRequest request) {
        return new User(request.firstname(), request.lastname(), request.username(), request.email(), request.password());
    }
}