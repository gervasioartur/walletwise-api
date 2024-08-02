package com.walletwise.infra.gateways.mappers.security;

import com.walletwise.application.dto.security.SignupRequest;
import com.walletwise.domain.entities.models.User;

public class UserDTOMapper {
    public User toUserDomainObject(SignupRequest request) {
        return new User(request.firstname(), request.lastname(), request.username(), request.email(), request.password());
    }
}