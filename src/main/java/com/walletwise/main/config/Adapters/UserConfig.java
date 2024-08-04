package com.walletwise.main.config.Adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.useCases.authentication.Signin;
import com.walletwise.domain.useCases.authentication.Signup;
import com.walletwise.infrastructure.adapters.LoadUserAdapter;
import com.walletwise.infrastructure.adapters.UserAdapter;
import com.walletwise.infrastructure.gateways.mappers.security.UserDTOMapper;
import com.walletwise.infrastructure.gateways.mappers.security.UserEntityMapper;
import com.walletwise.infrastructure.persistence.repositories.security.IRoleRepository;
import com.walletwise.infrastructure.persistence.repositories.security.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public Signup signup(IUserAdapter userAdapter, ICryptoAdapter cryptoAdapter) {
        return new Signup(userAdapter, cryptoAdapter);
    }

    @Bean
    public Signin signin(IUserAdapter userAdapter, IAuthAdapter authAdapter) {
        return new Signin(userAdapter, authAdapter);
    }

    @Bean
    public LoadUserAdapter loadUserAdapter(IUserRepository userRepository) {
        return new LoadUserAdapter(userRepository);
    }

    @Bean
    UserDTOMapper userDTOMapper() {
        return new UserDTOMapper();
    }

    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    public UserAdapter userAdapter(IUserRepository userRepository, IRoleRepository roleRepository, UserEntityMapper userEntityMapper) {
        return new UserAdapter(userRepository, roleRepository, userEntityMapper);
    }
}
