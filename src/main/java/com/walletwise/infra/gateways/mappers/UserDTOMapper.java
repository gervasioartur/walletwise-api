package com.walletwise.infra.gateways.mappers;

import com.walletwise.domain.entities.model.User;
import com.walletwise.infra.resource.http.SinupRequest;

public class UserDTOMapper {
    public User toUserDomainObject(SinupRequest request) {
        return new User(request.firstname(), request.lastname(), request.username(), request.email(), request.password());
    }
}