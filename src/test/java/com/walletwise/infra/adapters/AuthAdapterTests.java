package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IAuthAdapter;
import com.walletwise.domain.entities.models.Profile;
import com.walletwise.domain.entities.models.Session;
import com.walletwise.domain.entities.models.ValidationToken;
import com.walletwise.infra.gateways.mappers.SessionEntityMapper;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.gateways.mappers.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.token.GenerateToken;
import com.walletwise.infra.persistence.entities.security.SessionEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.infra.persistence.entities.security.ValidationTokenEntity;
import com.walletwise.infra.persistence.repositories.walletwise.ISessionEntityRepository;
import com.walletwise.infra.persistence.repositories.walletwise.IUserRepository;
import com.walletwise.infra.persistence.repositories.walletwise.IValidationTokenEntityRepository;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private UserEntityMapper userEntityMapper;
    @MockBean
    private ISessionEntityRepository sessionEntityRepository;
    @MockBean
    private SessionEntityMapper sessionEntityMapper;

    @BeforeEach
    void setup() {
        this.authAdapter = new AuthAdapter(authenticationManager,
                generateToken,
                validationTokenEntityMapper,
                validationTokenEntityRepository,
                userRepository,
                userEntityMapper,
                sessionEntityRepository,
                sessionEntityMapper);
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
        ValidationToken validationToken = Mocks.validationTokenWithOutIdFactory();

        UserEntity userEntity = Mocks.savedUserEntityFactory();
        userEntity.setId(validationToken.getUserId());

        ValidationTokenEntity toSaveValidationTokenEntity = Mocks.validationTokenEntityFactory();
        toSaveValidationTokenEntity.setUser(userEntity);

        ValidationTokenEntity savedValidationTokenEntity = Mocks.validationTokenEntityFactory();
        savedValidationTokenEntity.setId(UUID.randomUUID());

        ValidationToken savedValidationToken = Mocks.validationTokenFactory(savedValidationTokenEntity);


        Mockito.when(this.validationTokenEntityMapper.toValidationTokenEntity(validationToken))
                .thenReturn(toSaveValidationTokenEntity);
        Mockito.when(this.userRepository.findById(validationToken.getUserId())).thenReturn(Optional.of(userEntity));
        Mockito.when(this.validationTokenEntityRepository.save(toSaveValidationTokenEntity))
                .thenReturn(savedValidationTokenEntity);
        Mockito.when(this.validationTokenEntityMapper.toValidationTokenDomainObject(savedValidationTokenEntity))
                .thenReturn(savedValidationToken);

        ValidationToken result = this.authAdapter.saveValidationToken(validationToken);

        Assertions.assertThat(result).isEqualTo(savedValidationToken);
        Mockito.verify(this.validationTokenEntityMapper, Mockito.times(1))
                .toValidationTokenEntity(validationToken);
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(userEntity.getId());
        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .save(toSaveValidationTokenEntity);
        Mockito.verify(this.validationTokenEntityMapper, Mockito.times(1))
                .toValidationTokenDomainObject(savedValidationTokenEntity);
    }

    @Test
    @DisplayName("Should return null if the Validation token does not exist by token")
    void shouldReturnNullIfTheValidationTokenDoesNotExistByToken() {
        String token = UUID.randomUUID().toString();
        Mockito.when(this.validationTokenEntityRepository.findByTokenAndActive(token, true))
                .thenReturn(Optional.empty());

        ValidationToken result = this.authAdapter.findValidationTokenByToken(token);

        Assertions.assertThat(result).isNull();
        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .findByTokenAndActive(token, true);
    }

    @Test
    @DisplayName("Should return Validation token on success")
    void shouldReturnValidationTokenOnSuccess() {
        String token = UUID.randomUUID().toString();

        ValidationTokenEntity validationTokenEntity = Mocks.validationTokenEntityFactory();
        validationTokenEntity.setToken(token);

        ValidationToken validationToken = Mocks.validationTokenFactory(validationTokenEntity);

        Mockito.when(this.validationTokenEntityRepository.findByTokenAndActive(token, true))
                .thenReturn(Optional.of(validationTokenEntity));
        Mockito.when(this.validationTokenEntityMapper.toValidationTokenDomainObject(validationTokenEntity))
                .thenReturn(validationToken);

        ValidationToken result = this.authAdapter.findValidationTokenByToken(token);

        Assertions.assertThat(result.getToken()).isEqualTo(token);
        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .findByTokenAndActive(token, true);
        Mockito.verify(this.validationTokenEntityMapper, Mockito.times(1))
                .toValidationTokenDomainObject(validationTokenEntity);

    }

    @Test
    @DisplayName("Should do nothing if validation does not exist when trying to remove")
    void shouldRemoveDoNothingIdValidationTokenDoesNotExistWhenTryingToRemove() {
        UUID validationTokenId = UUID.randomUUID();

        Mockito.when(this.validationTokenEntityRepository.findByIdAndActive(validationTokenId, true))
                .thenReturn(Optional.empty());

        this.authAdapter.removeValidationToken(validationTokenId);

        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .findByIdAndActive(validationTokenId, true);
        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(0))
                .save(Mockito.any(ValidationTokenEntity.class));
    }

    @Test
    @DisplayName("Should remove validation token")
    void shouldRemoveValidationToken() {
        UUID validationTokenId = UUID.randomUUID();

        ValidationTokenEntity validationTokenEntity = Mocks.validationTokenEntityFactory();
        Mockito.when(this.validationTokenEntityRepository.findByIdAndActive(validationTokenId, true))
                .thenReturn(Optional.of(validationTokenEntity));

        this.authAdapter.removeValidationToken(validationTokenId);

        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .findByIdAndActive(validationTokenId, true);
        Mockito.verify(this.validationTokenEntityRepository, Mockito.times(1))
                .save(Mockito.any(ValidationTokenEntity.class));
    }

    @Test
    @DisplayName("Should save session info")
    void shouldSaveSessionInfo() {
        Session toSaveSession = Mocks.sessionWithOutIdDomainObjectFactory();
        SessionEntity toSaveSessionEntity = Mocks.formSessionToSessionEntityFactory(toSaveSession);

        SessionEntity savedSessionEntity = Mocks.sessionEntityFactory(toSaveSessionEntity);
        Session savedSessionDomainObject = Mocks.formSessionEntityToSessionFactory(savedSessionEntity);

        Mockito.when(this.sessionEntityMapper.toSessionEntity(toSaveSession)).thenReturn(toSaveSessionEntity);
        Mockito.when(this.sessionEntityRepository.save(toSaveSessionEntity))
                .thenReturn(savedSessionEntity);
        Mockito.when(this.sessionEntityMapper.toSessionDomainObject(savedSessionEntity))
                .thenReturn(savedSessionDomainObject);


        Session result = this.authAdapter.saveSession(toSaveSession);

        Assertions.assertThat(result).isEqualTo(savedSessionDomainObject);
        Mockito.verify(this.sessionEntityMapper, Mockito.times(1))
                .toSessionEntity(toSaveSession);
        Mockito.verify(this.sessionEntityRepository, Mockito.times(1))
                .save(toSaveSessionEntity);
        Mockito.verify(this.sessionEntityMapper, Mockito.times(1))
                .toSessionDomainObject(savedSessionEntity);
    }

    @Test
    @DisplayName("Should return null if user does not exist")
    void shouldReturnNullIfUserDoesNotExist() {
        UserEntity userEntity = Mocks.savedUserEntityFactory();

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (userEntity.getUsername(), userEntity.getPassword());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(this.userRepository.findByUsernameAndActive(userEntity.getUsername(), true))
                .thenReturn(Optional.empty());

        Profile result = this.authAdapter.getUserProfile();

        Assertions.assertThat(result).isNull();

        Mockito.verify(this.userRepository, Mockito.times(1))
                .findByUsernameAndActive(userEntity.getUsername(), true);
    }

    @Test
    @DisplayName("Should Return profile on success")
    void shouldReturnProfileOnSuccess() {
        UserEntity userEntity = Mocks.savedUserEntityFactory();

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (userEntity.getUsername(), userEntity.getPassword());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(this.userRepository.findByUsernameAndActive(userEntity.getUsername(), true))
                .thenReturn(Optional.empty());


        Profile result = this.authAdapter.getUserProfile();

        Assertions.assertThat(result).isNull();

        Mockito.verify(this.userRepository, Mockito.times(1))
                .findByUsernameAndActive(userEntity.getUsername(), true);
    }

    @Test
    @DisplayName("Should close all user sessions")
    void shouldCloseAllUerSession() {
        UUID userId = UUID.randomUUID();
        this.authAdapter.closeAllSessions(userId);
        Mockito.verify(this.sessionEntityRepository, Mockito.times(1))
                .deleteByUserId(userId);
    }
}
