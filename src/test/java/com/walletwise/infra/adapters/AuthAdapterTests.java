package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.infra.gateways.mappers.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.token.GenerateToken;
import com.walletwise.infra.persistence.entities.ValidationTokenEntity;
import com.walletwise.infra.persistence.repositories.IValidationTokenEntityRepository;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AuthAdapterTests {
    private IAuthAdapter authAdapter;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private GenerateToken generateToken;
    @MockBean
    private ValidationTokenEntityMapper validationTokenEntityMapper;
    @MockBean
    private IValidationTokenEntityRepository validationTokenEntityRepository;

    @BeforeEach
    void setup() {
        this.authAdapter = new AuthAdapter(authenticationManager,
                generateToken,
                validationTokenEntityMapper,
                validationTokenEntityRepository);
    }

    @Test
    @DisplayName("Should return null if authentication throws ")
    void shouldReturnNullIfAuthenticationThrows() {
        String username = Mocks.faker.name().username();
        String password = Mocks.faker.internet().password();

        Mockito.doThrow(new AuthenticationException("Bad credentials") {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                }).when(this.authenticationManager)
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

        String result = this.authAdapter.authenticate(username, password);

        Assertions.assertThat(result).isNull();
        Mockito.verify(this.authenticationManager, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(this.generateToken, Mockito.times(0))
                .generate(username);
    }


    @Test
    @DisplayName("should return access token ")
    void shouldReturnAccessToken() {
        String username = Mocks.faker.name().username();
        String password = Mocks.faker.internet().password();
        String accessToken = UUID.randomUUID().toString();

        Mockito.when(this.authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return List.of();
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return true;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                    }

                    @Override
                    public String getName() {
                        return username;
                    }
                });

        Mockito.when(this.generateToken.generate(username)).thenReturn(accessToken);

        String result = this.authAdapter.authenticate(username, password);

        Assertions.assertThat(result).isEqualTo(accessToken);
        Mockito.verify(this.authenticationManager, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(this.generateToken, Mockito.times(1))
                .generate(username);
    }

    @Test
    @DisplayName("Should save ValidationToken information")
    void shouldSaveValidationTokenInformation() {
        ValidationToken validationToken = Mocks.validationWithOutIdTokenFactory();

        ValidationTokenEntity toSaveValidationTokenEntity = Mocks.validationTokenEntityFactory();

        ValidationTokenEntity savedValidationTokenEntity = Mocks.validationTokenEntityFactory();
        savedValidationTokenEntity.setId(UUID.randomUUID());

        ValidationToken savedValidationToken = Mocks.validationTokenFactory(savedValidationTokenEntity);


        Mockito.when(this.validationTokenEntityMapper.toValidationTokenEntity(validationToken))
                .thenReturn(toSaveValidationTokenEntity);
        Mockito.when(this.validationTokenEntityRepository.save(toSaveValidationTokenEntity))
                .thenReturn(savedValidationTokenEntity);
        Mockito.when(this.validationTokenEntityMapper.toValidationTokenDomainObject(savedValidationTokenEntity))
                .thenReturn(savedValidationToken);

        ValidationToken result = this.authAdapter.saveValidationToken(validationToken);

        Assertions.assertThat(result).isEqualTo(savedValidationToken);
        Mockito.verify(this.validationTokenEntityMapper, Mockito.times(1))
                .toValidationTokenEntity(validationToken);
        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .save(toSaveValidationTokenEntity);
        Mockito.verify(this.validationTokenEntityMapper, Mockito.times(1))
                .toValidationTokenDomainObject(savedValidationTokenEntity);
    }
}
