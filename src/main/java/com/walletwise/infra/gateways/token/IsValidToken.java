package com.walletwise.infra.gateways.token;

import com.walletwise.infra.persistence.entities.security.SessionEntity;
import com.walletwise.infra.persistence.repositories.walletwise.ISessionEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

public class IsValidToken {
    private final GetUsernameFromToken getUsernameFromToken;
    private final ISessionEntityRepository sessionEntityRepository;

    public IsValidToken(GetUsernameFromToken getUsernameFromToken,
                        ISessionEntityRepository sessionEntityRepository) {
        this.getUsernameFromToken = getUsernameFromToken;
        this.sessionEntityRepository = sessionEntityRepository;
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken.get(token);
        boolean isValid = false;
        if (username.equals(userDetails.getUsername())) {
            Optional<SessionEntity> sessionEntity = this.sessionEntityRepository.findByTokenAndActive(token, true);
            if (sessionEntity.isPresent()) {
                if (LocalDateTime.now().isAfter(sessionEntity.get().getExpirationDate())) {
                    sessionEntity.get().setActive(false);
                    this.sessionEntityRepository.save(sessionEntity.get());
                } else {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
}