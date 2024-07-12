package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.authentication.IAuthenticationGateway;
import com.walletwise.application.gateways.hash.IEncoderGateway;
import com.walletwise.application.gateways.user.ICreateUserGateway;
import com.walletwise.application.gateways.user.IFindUserByEmailGateway;
import com.walletwise.application.gateways.user.IFindUserByUserNameGateway;
import com.walletwise.application.gateways.user.IFindUserRoleByNameGateway;
import com.walletwise.application.useCases.contracts.ICreateUserUseCase;
import com.walletwise.domain.entities.Role;
import com.walletwise.domain.entities.User;
import com.walletwise.domain.entities.UserAccount;
import com.walletwise.domain.enums.RoleEnum;
import com.walletwise.domain.exceptions.BusinessException;
import com.walletwise.domain.exceptions.UnexpectedException;

public class CreateUserUseCase implements ICreateUserUseCase {
    private final IFindUserByUserNameGateway findUserByUserNameGateway;
    private final IFindUserByEmailGateway findUserByEmailGateway;
    private final IEncoderGateway encoder;
    private final IFindUserRoleByNameGateway findUserRoleByName;
    private final ICreateUserGateway createUserGateway;
    private final IAuthenticationGateway authentication;

    public CreateUserUseCase(
            IFindUserByUserNameGateway findUserByUserNameGateway,
            IFindUserByEmailGateway findUserByEmailGateway,
            IEncoderGateway encoder,
            IFindUserRoleByNameGateway findUserRoleByName,
            ICreateUserGateway createUserGateway,
            IAuthenticationGateway authentication
    ) {

        this.findUserByUserNameGateway = findUserByUserNameGateway;
        this.findUserByEmailGateway = findUserByEmailGateway;
        this.encoder = encoder;
        this.findUserRoleByName = findUserRoleByName;
        this.createUserGateway = createUserGateway;
        this.authentication = authentication;
    }

    @Override
    public UserAccount create(User user) {
        User userResult = this.findUserByUserNameGateway.find(user.username());
        if (userResult != null)
            throw new BusinessException("The username is already in use. Please try another username.");

        userResult = this.findUserByEmailGateway.find(user.email());
        if (userResult != null) throw new BusinessException("The email is already in use. Please try another email.");

        Role roleResult = this.findUserRoleByName.find(RoleEnum.USER.getValue());
        if (roleResult == null)
            throw new UnexpectedException("Something went wrong while saving the information. Please concat the administrator.");

        String encodedPassword = this.encoder.encode(user.password());
        userResult = new User(user.firstname(), user.lastname(), user.username(), user.email(), encodedPassword);
        userResult = this.createUserGateway.create(userResult);
        String accessToken = this.authentication.authenticate(userResult.username(), user.password());
        return new UserAccount(accessToken);
    }
}
