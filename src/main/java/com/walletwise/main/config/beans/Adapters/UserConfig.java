package com.walletwise.main.config.beans.Adapters;

import com.walletwise.infra.adapters.LoadUserByUsernameAdapter;
import com.walletwise.infra.adapters.UserAdapter;
import com.walletwise.infra.gateways.mappers.UserDTOMapper;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.persistence.repositories.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public LoadUserByUsernameAdapter loadUserByUsernameAdapter(IUserRepository userRepository) {
        return new LoadUserByUsernameAdapter(userRepository);
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
