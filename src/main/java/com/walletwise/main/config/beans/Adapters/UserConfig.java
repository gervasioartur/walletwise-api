package com.walletwise.main.config.beans.Adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.useCases.auth.Signin;
import com.walletwise.domain.useCases.auth.Signup;
import com.walletwise.infra.adapters.LoadUserAdapter;
import com.walletwise.infra.adapters.UserAdapter;
import com.walletwise.infra.gateways.mappers.UserDTOMapper;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.persistence.repositories.walletwise.IUserRepository;
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
    public UserAdapter userAdapter(IUserRepository userRepository, UserEntityMapper userEntityMapper) {
        return new UserAdapter(userRepository, userEntityMapper);
    }
}
