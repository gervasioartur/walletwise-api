package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.IFindUserByEmailGateway;
import com.walletwise.application.gateways.IFindUserByUserNameGateway;
import com.walletwise.application.useCases.contracts.ICreateUserUseCase;
import com.walletwise.domain.entities.User;
import com.walletwise.domain.entities.UserAccount;
import com.walletwise.domain.exceptions.BusinessException;

public class CreateUserUseCase implements ICreateUserUseCase {
    private final IFindUserByUserNameGateway findUserByUserNameGateway;
    private final IFindUserByEmailGateway findUserByEmailGateway;

    public CreateUserUseCase(IFindUserByUserNameGateway findUserByUserNameGateway, IFindUserByEmailGateway findUserByEmailGateway) {
        this.findUserByUserNameGateway = findUserByUserNameGateway;
        this.findUserByEmailGateway =  findUserByEmailGateway;
    }

    @Override
    public UserAccount create(User user) {
        User userResult = this.findUserByUserNameGateway.find(user.username());
        if (userResult != null)
            throw new BusinessException("The username is already in use. Please try another username.");

        userResult =  this.findUserByEmailGateway.find(user.email());
        if (userResult != null)
            throw new BusinessException("The email is already in use. Please try another email.");

        return null;
    }
}
