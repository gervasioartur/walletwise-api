package com.walletwise.application.useCases.contracts;

import com.walletwise.domain.entities.User;
import com.walletwise.domain.entities.UserAccount;

public interface ICreateUserUseCase {
    UserAccount create(User user);
}