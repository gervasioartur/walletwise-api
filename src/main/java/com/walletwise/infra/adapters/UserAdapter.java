package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.model.User;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.persistence.entities.UserEntity;
import com.walletwise.infra.persistence.repositories.IUserRepository;

import java.util.Optional;

public class UserAdapter implements IUserAdapter {
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public UserAdapter(IUserRepository userRepository, UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = this.userEntityMapper.toUserEntity(user);
        userEntity = this.userRepository.save(userEntity);
        return this.userEntityMapper.toDomainObject(userEntity);
    }

    @Override
    public User findByUsername(String username) {
        Optional<UserEntity> userEntity = this.userRepository.findByUsernameAndActive(username, true);
        return userEntity.map(this.userEntityMapper::toDomainObject).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> userEntity = this.userRepository.findByEmailAndActive(email, true);
        return userEntity.map(this.userEntityMapper::toDomainObject).orElse(null);
    }
}
