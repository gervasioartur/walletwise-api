package com.walletwise.infrastructure.gateways.user;

import com.walletwise.application.gateways.user.ICreateUserGateway;
import com.walletwise.domain.entities.User;
import com.walletwise.infrastructure.gateways.mappers.UserEntityMapper;
import com.walletwise.infrastructure.persistence.entities.UserEntity;
import com.walletwise.infrastructure.persistence.repositories.IUserRepository;

public class CreateUserGateway implements ICreateUserGateway {
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public CreateUserGateway(IUserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User create(User userDomainObject) {
        UserEntity userEntity = this.userEntityMapper.toUserEntity(userDomainObject);
        userEntity.setActive(true);
        userEntity = this.userRepository.save(userEntity);
        return this.userEntityMapper.toDomainObject(userEntity);
    }
}
