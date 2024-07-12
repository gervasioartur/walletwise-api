package com.walletwise.application.useCases.implementations;

import com.walletwise.application.gateways.IFindUserByUserNameGateway;
import com.walletwise.application.useCases.contracts.ICreateUserUseCase;
import com.walletwise.domain.entities.User;
import com.walletwise.domain.entities.UserAccount;
import com.walletwise.domain.exceptions.BusinessException;

public class CreateUserUseCase implements ICreateUserUseCase {
   private final IFindUserByUserNameGateway findUserByUserNameGateway;

   public  CreateUserUseCase(IFindUserByUserNameGateway findUserByUserNameGateway){
       this.findUserByUserNameGateway = findUserByUserNameGateway;
   }

    @Override
    public UserAccount create(User user) {
        User userResult = findUserByUserNameGateway.find(user.username());

        if(userResult != null) throw new BusinessException("The username is already in use. Please try another username.");
        return null;
    }
}
