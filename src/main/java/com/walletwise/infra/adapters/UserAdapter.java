package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.persistence.entities.UserEntity;
import com.walletwise.infra.persistence.repositories.IUserRepository;

import java.util.Optional;

public class UserAdapter implements IUserAdapter {
    private final IUserRepository userRepository;
    private final UserEntityMapper mapper;

    public UserAdapter(IUserRepository userRepository, UserEntityMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User findByUsername(String username) {
        Optional<UserEntity> userEntity = this.userRepository.findByUsernameAndActive(username, true);
        return userEntity.map(this.mapper::toDomainObject).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> userEntity = this.userRepository.findByEmailAndActive(email, true);
        return userEntity.map(this.mapper::toDomainObject).orElse(null);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = this.mapper.toUserEntity(user);
        userEntity.setActive(true);
        userEntity = this.userRepository.save(userEntity);
        return this.mapper.toDomainObject(userEntity);
    }
}
