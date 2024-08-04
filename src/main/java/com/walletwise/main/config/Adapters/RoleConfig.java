package com.walletwise.main.config.Adapters;

import com.walletwise.infrastructure.adapters.RoleAdapter;
import com.walletwise.infrastructure.gateways.mappers.security.RoleEntityMapper;
import com.walletwise.infrastructure.persistence.repositories.security.IRoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {


    @Bean
    RoleEntityMapper roleEntityMapper() {
        return new RoleEntityMapper();
    }

    @Bean
    public RoleAdapter roleAdapter(IRoleRepository roleRepository, RoleEntityMapper roleEntityMapper) {
        return new RoleAdapter(roleRepository, roleEntityMapper);
    }
}
