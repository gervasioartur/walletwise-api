package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.hash.IEncoder;
import com.walletwise.application.gateways.user.IFindUserByEmailGateway;
import com.walletwise.application.gateways.user.IFindUserByUserNameGateway;
import com.walletwise.application.gateways.user.IFindUserRoleByName;
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
    private final IEncoder encoder;
    private final IFindUserRoleByName findUserRoleByName;

    public CreateUserUseCase(
            IFindUserByUserNameGateway findUserByUserNameGateway,
            IFindUserByEmailGateway findUserByEmailGateway,
            IEncoder encoder,
            IFindUserRoleByName findUserRoleByName
    ) {

        this.findUserByUserNameGateway = findUserByUserNameGateway;
        this.findUserByEmailGateway = findUserByEmailGateway;
        this.encoder = encoder;
        this.findUserRoleByName = findUserRoleByName;
    }

    @Override
    public UserAccount create(User user) {
        User userResult = this.findUserByUserNameGateway.find(user.username());
        if (userResult != null) throw new BusinessException("The username is already in use. Please try another username.");

        userResult = this.findUserByEmailGateway.find(user.email());
        if (userResult != null) throw new BusinessException("The email is already in use. Please try another email.");

        this.encoder.encode(user.password());
        Role roleResult =  this.findUserRoleByName.find(RoleEnum.USER.getValue());
        if(roleResult == null) throw new UnexpectedException("Something went wrong while saving the information. Please concat the administrator.");
        return null;
    }
}
