package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.infra.gateways.mappers.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.token.GenerateToken;
import com.walletwise.infra.persistence.entities.ValidationTokenEntity;
import com.walletwise.infra.persistence.repositories.IValidationTokenEntityRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthAdapter implements IAuthAdapter {
    private final AuthenticationManager authenticationManager;
    private final GenerateToken generateToken;
    private final ValidationTokenEntityMapper validationTokenEntityMapper;
    private final IValidationTokenEntityRepository validationTokenEntityRepository;

    public AuthAdapter(AuthenticationManager authenticationManager,
                       GenerateToken generateToken,
                       ValidationTokenEntityMapper validationTokenEntityMapper,
                       IValidationTokenEntityRepository validationTokenEntityRepository) {
        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
        this.validationTokenEntityMapper = validationTokenEntityMapper;
        this.validationTokenEntityRepository = validationTokenEntityRepository;
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
        entity.setActive(true);
        entity = this.validationTokenEntityRepository.save(entity);
        return this.validationTokenEntityMapper.toValidationTokenDomainObject(entity);
    }
}