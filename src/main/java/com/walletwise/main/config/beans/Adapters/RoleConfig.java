package com.walletwise.main.config.beans.Adapters;

import com.walletwise.infra.adapters.RoleAdapter;
import com.walletwise.infra.gateways.mappers.RoleEntityMapper;
import com.walletwise.infra.persistence.repositories.IRoleRepository;
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
