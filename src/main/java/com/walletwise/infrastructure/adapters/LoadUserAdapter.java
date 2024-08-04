package com.walletwise.infrastructure.adapters;

import com.walletwise.infrastructure.persistence.repositories.security.IUserRepository;
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
