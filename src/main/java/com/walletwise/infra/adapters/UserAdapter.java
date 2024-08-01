package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.enums.RoleEnum;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.gateways.mappers.security.UserEntityMapper;
import com.walletwise.infra.persistence.entities.security.RoleEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.infra.persistence.repositories.walletwise.IRoleRepository;
import com.walletwise.infra.persistence.repositories.walletwise.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserAdapter implements IUserAdapter {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserEntityMapper mapper;

    public UserAdapter(IUserRepository userRepository, IRoleRepository roleRepository, UserEntityMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
    public User findById(UUID id) {
        Optional<UserEntity> userEntity = this.userRepository.findByIdAndActive(id, true);
        return userEntity.map(this.mapper::toDomainObject).orElse(null);
    }

    @Override
    public User save(User user) {
        RoleEntity roleEntity = this.roleRepository
                .findByNameAndActive(RoleEnum.USER.getValue(), true)
                .orElseThrow(() -> new NotFoundException("Role not found!"));

        UserEntity userEntity = this.mapper.toUserEntity(user);
        userEntity.setActive(true);
        userEntity.setRoles(List.of(roleEntity));
        userEntity = this.userRepository.save(userEntity);
        return this.mapper.toDomainObject(userEntity);
    }

    @Override
    public void updatePassword(UUID userId, String password) {
        this.userRepository.findByIdAndActive(userId, true)
                .ifPresent(entity -> {
                    entity.setPassword(password);
                    this.userRepository.save(entity);
                });
    }
}
