package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.entities.models.Session;
import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.infra.gateways.mappers.security.SessionEntityMapper;
import com.walletwise.infra.gateways.mappers.security.UserEntityMapper;
import com.walletwise.infra.gateways.mappers.security.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.token.GenerateToken;
import com.walletwise.infra.persistence.entities.security.SessionEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.infra.persistence.entities.security.ValidationTokenEntity;
import com.walletwise.infra.persistence.repositories.security.ISessionEntityRepository;
import com.walletwise.infra.persistence.repositories.security.IUserRepository;
import com.walletwise.infra.persistence.repositories.security.IValidationTokenEntityRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class AuthAdapter implements IAuthAdapter {
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;
    private final ValidationTokenEntityMapper validationTokenEntityMapper;
    private final IValidationTokenEntityRepository validationTokenEntityRepository;
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final ISessionEntityRepository sessionEntityRepository;
    private final SessionEntityMapper sessionEntityMapper;


    public AuthAdapter(AuthenticationManager authenticationManager,
                       GenerateToken generateToken,
                       ValidationTokenEntityMapper validationTokenEntityMapper,
                       IValidationTokenEntityRepository validationTokenEntityRepository,
                       IUserRepository userRepository,
                       UserEntityMapper userEntityMapper,
                       ISessionEntityRepository sessionEntityRepository,
                       SessionEntityMapper sessionEntityMapper) {

        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
        this.validationTokenEntityMapper = validationTokenEntityMapper;
        this.validationTokenEntityRepository = validationTokenEntityRepository;
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.sessionEntityRepository = sessionEntityRepository;
        this.sessionEntityMapper = sessionEntityMapper;
    }

    @Override

    public String authenticate(String username, String password) {
        try {
            Authentication auth = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return this.generateToken.generate(auth.getName());
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ValidationToken saveValidationToken(ValidationToken validationToken) {
        ValidationTokenEntity entity = this.validationTokenEntityMapper.toValidationTokenEntity(validationToken);
        UserEntity userEntity = this.userRepository.findById(entity.getUser().getId()).orElseThrow(RuntimeException::new);
        entity.setUser(userEntity);
        entity.setActive(true);
        entity = this.validationTokenEntityRepository.save(entity);
        return this.validationTokenEntityMapper.toValidationTokenDomainObject(entity);
    }

    @Override
    public ValidationToken findValidationTokenByToken(String token) {
        Optional<ValidationTokenEntity> entity = this.validationTokenEntityRepository
                .findByTokenAndActive(token, true);
        return entity.map(this.validationTokenEntityMapper::toValidationTokenDomainObject).orElse(null);
    }

    @Override
    public void removeValidationToken(UUID id) {
        this.validationTokenEntityRepository.findByIdAndActive(id, true)
                .ifPresent(entity -> {
                    entity.setActive(false);
                    this.validationTokenEntityRepository.save(entity);
                });
    }

    @Override
    public Session saveSession(Session session) {
        SessionEntity entity = this.sessionEntityMapper.toSessionEntity(session);
        entity.setActive(true);
        entity = this.sessionEntityRepository.save(entity);
        return this.sessionEntityMapper.toSessionDomainObject(entity);
    }

    @Override
    public Profile getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> userEntity = this.userRepository
                .findByUsernameAndActive(authentication.getName(), true);
        return userEntity.map(this.userEntityMapper::toProfile).orElse(null);
    }

    @Override
    public void closeAllSessions(UUID userId) {
        this.sessionEntityRepository.deleteByUserId(userId);
    }
}