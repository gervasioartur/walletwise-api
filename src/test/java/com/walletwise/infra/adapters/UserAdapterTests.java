package com.walletwise.infra.adapters;

import com.github.javafaker.Faker;
import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.model.User;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.persistence.entities.UserEntity;
import com.walletwise.infra.persistence.repositories.IUserRepository;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserAdapterTests {
    private IUserAdapter userAdapter;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private UserEntityMapper mapper;

    private Faker faker = new Faker();

    @BeforeEach
    void setup() {
        this.userAdapter = new UserAdapter(userRepository, mapper);
    }

    @Test
    @DisplayName("Should return null if user does not exist by username")
    void shouldReturnNullIfUserDOesNotExistByUsername() {
        String username = faker.name().username();

        Mockito.when(this.userRepository.findByUsernameAndActive(username, true)).thenReturn(Optional.empty());

        User userDomainObject = this.userAdapter.findByUsername(username);

        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByUsernameAndActive(username, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by username")
    void shouldReturnUserDomainObjectIfExistsByUsername() {
        User savedUserDomainObject = Mocks.savedUserFactory(Mocks.userWithoutIdFactory());
        UserEntity savedUserEntity = Mocks.fromUserToUserEntityFactory(savedUserDomainObject);

        Mockito.when(this.userRepository.findByUsernameAndActive(savedUserDomainObject.getUsername(), true))
                .thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.mapper.toDomainObject(savedUserEntity)).thenReturn(savedUserDomainObject);

        User userDomainObject = this.userAdapter.findByUsername(savedUserDomainObject.getUsername());

        Assertions.assertThat(userDomainObject).isEqualTo(savedUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByUsernameAndActive(savedUserDomainObject.getUsername(), true);
        Mockito.verify(this.mapper, Mockito.times(1)).toDomainObject(savedUserEntity);
    }

    @Test
    @DisplayName("Should return null if user does not exist by email")
    void shouldReturnNullIfUserDOesNotExistByEmail() {
        String email = faker.internet().emailAddress();

        Mockito.when(this.userRepository.findByEmailAndActive(email, true)).thenReturn(Optional.empty());

        User userDomainObject = this.userAdapter.findByEmail(email);

        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmailAndActive(email, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by email")
    void shouldReturnUserDomainObjectIfExistsByEMaile() {
        User savedUserDomainObject = Mocks.savedUserFactory(Mocks.userWithoutIdFactory());
        UserEntity savedUserEntity = Mocks.fromUserToUserEntityFactory(savedUserDomainObject);

        Mockito.when(this.userRepository.findByEmailAndActive(savedUserDomainObject.getEmail(), true))
                .thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.mapper.toDomainObject(savedUserEntity)).thenReturn(savedUserDomainObject);

        User userDomainObject = this.userAdapter.findByEmail(savedUserDomainObject.getEmail());

        Assertions.assertThat(userDomainObject).isEqualTo(savedUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmailAndActive(savedUserDomainObject.getEmail(), true);
        Mockito.verify(this.mapper, Mockito.times(1)).toDomainObject(savedUserEntity);
    }

    @Test
    @DisplayName("Should return user domain object on save success")
    void shouldReturnUserDomainObjectOnSuccess() {
        User toSaveUserDomainObject = Mocks.userWithoutIdFactory();
        UserEntity toSaveUserEntity = Mocks.fromUserToUserEntityFactory(toSaveUserDomainObject);

        UserEntity savedUserEntity = toSaveUserEntity;
        savedUserEntity.setId(UUID.randomUUID());
        User savedUserDomainObject = Mocks.fromUserEntityToUserFactory(savedUserEntity);

        Mockito.when(this.mapper.toUserEntity(toSaveUserDomainObject)).thenReturn(toSaveUserEntity);
        Mockito.when(this.userRepository.save(toSaveUserEntity)).thenReturn(savedUserEntity);
        Mockito.when(this.mapper.toDomainObject(savedUserEntity)).thenReturn(savedUserDomainObject);

        User userDomainObjectResult = this.userAdapter.save(toSaveUserDomainObject);

        Assertions.assertThat(userDomainObjectResult).isEqualTo(savedUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(toSaveUserEntity);
        Mockito.verify(this.mapper, Mockito.times(1)).toDomainObject(savedUserEntity);
    }
}
