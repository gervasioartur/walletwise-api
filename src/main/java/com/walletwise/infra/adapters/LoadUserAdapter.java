package com.walletwise.infra.adapters;

import com.walletwise.infra.persistence.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoadUserAdapter implements UserDetailsService {
    private final IUserRepository userRepository;

    public LoadUserAdapter(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
