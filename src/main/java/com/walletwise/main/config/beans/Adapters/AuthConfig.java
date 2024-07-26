package com.walletwise.main.config.beans.Adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.domain.adapters.IEmailAdapter;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.useCases.auth.ConfirmPasswordRecovery;
import com.walletwise.domain.useCases.auth.GetUserProfile;
import com.walletwise.domain.useCases.auth.PasswordRecovery;
import com.walletwise.infra.adapters.AuthAdapter;
import com.walletwise.infra.adapters.CryptoAdapter;
import com.walletwise.infra.adapters.LoadUserAdapter;
import com.walletwise.infra.gateways.mappers.security.SessionEntityMapper;
import com.walletwise.infra.gateways.mappers.security.UserEntityMapper;
import com.walletwise.infra.gateways.mappers.security.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.security.SignKey;
import com.walletwise.infra.gateways.token.*;
import com.walletwise.infra.persistence.repositories.walletwise.ISessionEntityRepository;
import com.walletwise.infra.persistence.repositories.walletwise.IUserRepository;
import com.walletwise.infra.persistence.repositories.walletwise.IValidationTokenEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {
    private final IUserRepository userRepository;
    @Value("${app.server.url}")
    private String appServerUrl;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthAdapter authAdapter(AuthenticationManager authenticationManager,
                                   GenerateToken generateToken,
                                   ValidationTokenEntityMapper validationTokenEntityMapper,
                                   IValidationTokenEntityRepository validationTokenEntityRepository,
                                   IUserRepository userRepository,
                                   UserEntityMapper userEntityMapper,
                                   ISessionEntityRepository sessionEntityRepository,
                                   SessionEntityMapper sessionEntityMapper) {

        return new AuthAdapter(authenticationManager,
                generateToken,
                validationTokenEntityMapper,
                validationTokenEntityRepository,
                userRepository,
                userEntityMapper,
                sessionEntityRepository,
                sessionEntityMapper);
    }

    @Bean
    public PasswordRecovery passwordRecovery(IUserAdapter userAdapter,
                                             ICryptoAdapter cryptoAdapter,
                                             IAuthAdapter authAdapter,
                                             IEmailAdapter emailAdapter) {
        return new PasswordRecovery(userAdapter, cryptoAdapter, authAdapter, emailAdapter, appServerUrl);
    }

    @Bean
    public ConfirmPasswordRecovery confirmPasswordRecovery(IAuthAdapter authAdapter,
                                                           IUserAdapter userAdapter,
                                                           ICryptoAdapter cryptoAdapter) {
        return new ConfirmPasswordRecovery(authAdapter, userAdapter, cryptoAdapter);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new LoadUserAdapter(userRepository);
    }

    @Bean
    CryptoAdapter passwordEncoderGateway(PasswordEncoder passwordEncoder) {
        return new CryptoAdapter(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userDetailsService());
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public GetUserProfile getUserProfile(IAuthAdapter authAdapter) {
        return new GetUserProfile(authAdapter);
    }

    @Bean
    public SignKey singKey() {
        return new SignKey();
    }

    @Bean
    public CreateToken createToken() {
        return new CreateToken(singKey());
    }

    @Bean
    public ExtractAllClaims extractAllClaims() {
        return new ExtractAllClaims(singKey());
    }

    @Bean
    public ExtractClaim extractClaim() {
        return new ExtractClaim(extractAllClaims());
    }

    @Bean
    public GenerateToken generateToken() {
        return new GenerateToken(createToken());
    }

    @Bean
    public GetUsernameFromToken getUsernameFromToken() {
        return new GetUsernameFromToken(extractClaim());
    }

    @Bean
    public IsTokenExpired isTokenExpired() {
        return new IsTokenExpired(extractClaim());
    }

    @Bean
    public IsValidToken isValidToken(GetUsernameFromToken getUsernameFromToken,
                                     ISessionEntityRepository sessionEntityRepository) {
        return new IsValidToken(getUsernameFromToken, sessionEntityRepository);
    }

    @Bean
    public ValidateToken validateToken() {
        return new ValidateToken();
    }

    @Bean
    public ValidationTokenEntityMapper validationTokenEntityMapper() {
        return new ValidationTokenEntityMapper();
    }

    @Bean
    public SessionEntityMapper sessionEntityMapper(UserEntityMapper userEntityMapper) {
        return new SessionEntityMapper(userEntityMapper);
    }
}
