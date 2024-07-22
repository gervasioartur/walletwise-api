package com.walletwise.main.config.beans.security;

import com.walletwise.infra.adapters.AuthAdapter;
import com.walletwise.infra.adapters.CryptoAdapter;
import com.walletwise.infra.adapters.LoadUserAdapter;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.gateways.mappers.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.security.SignKey;
import com.walletwise.infra.gateways.token.*;
import com.walletwise.infra.persistence.repositories.IUserRepository;
import com.walletwise.infra.persistence.repositories.IValidationTokenEntityRepository;
import lombok.RequiredArgsConstructor;
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
public class ApplicationConfig {
    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthAdapter authAdapter (AuthenticationManager authenticationManager,
             GenerateToken generateToken,
             ValidationTokenEntityMapper validationTokenEntityMapper,
             IValidationTokenEntityRepository validationTokenEntityRepository) {

        return new AuthAdapter(authenticationManager, generateToken,validationTokenEntityMapper,validationTokenEntityRepository);
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
    public IsValidToken isValidToken() {
        return new IsValidToken(getUsernameFromToken(), isTokenExpired());
    }

    @Bean
    public ValidateToken validateToken() {
        return new ValidateToken();
    }

    @Bean
    public ValidationTokenEntityMapper validationTokenEntityMapper(){
        return new ValidationTokenEntityMapper();
    }
}
