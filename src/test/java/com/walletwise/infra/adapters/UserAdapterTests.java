package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IUserAdapter;
import com.walletwise.domain.entities.enums.RoleEnum;
import com.walletwise.domain.entities.exceptions.NotFoundException;
import com.walletwise.domain.entities.models.User;
import com.walletwise.infra.gateways.mappers.security.UserEntityMapper;
import com.walletwise.infra.persistence.entities.security.RoleEntity;
import com.walletwise.infra.persistence.entities.security.UserEntity;
import com.walletwise.infra.persistence.repositories.walletwise.IRoleRepository;
import com.walletwise.infra.persistence.repositories.walletwise.IUserRepository;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserAdapterTests {
    private IUserAdapter userAdapter;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IRoleRepository roleRepository;
    @MockBean
    private UserEntityMapper mapper;

    @BeforeEach
    void setup() {
        this.userAdapter = new UserAdapter(userRepository, roleRepository, mapper);
    }

    @Test
    @DisplayName("Should return null if user does not exist by username")
    void shouldReturnNullIfUserDOesNotExistByUsername() {
        String username = Mocks.faker.name().username();

        Mockito.when(this.userRepository.findByUsernameAndActive(username, true)).thenReturn(Optional.empty());

        User userDomainObject = this.userAdapter.findByUsername(username);

        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByUsernameAndActive(username, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by username")
    void shouldReturnUserDomainObjectIfExistsByUsername() {
        User savedUserDomainObject = Mocks.savedUserDomainObjectFactory(Mocks.userDomainObjectWithoutIdFactory());
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
        String email = Mocks.faker.internet().emailAddress();

        Mockito.when(this.userRepository.findByEmailAndActive(email, true)).thenReturn(Optional.empty());

        User userDomainObject = this.userAdapter.findByEmail(email);

        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmailAndActive(email, true);
    }

    @Test
    @DisplayName("Should return user domain object if exists by email")
    void shouldReturnUserDomainObjectIfExistsByEMaile() {
        User savedUserDomainObject = Mocks.savedUserDomainObjectFactory(Mocks.userDomainObjectWithoutIdFactory());
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
    @DisplayName("Should throw NotFoundException if roleRepository throws")
    void shouldThrowNotFoundExceptionIfRoleRepositoryThrows() {
        User toSaveUserDomainObject = Mocks.userDomainObjectWithoutIdFactory();
        UserEntity toSaveUserEntity = Mocks.fromUserToUserEntityFactory(toSaveUserDomainObject);

        UserEntity savedUserEntity = toSaveUserEntity;
        savedUserEntity.setId(UUID.randomUUID());
        User savedUserDomainObject = Mocks.fromUserEntityToUserFactory(savedUserEntity);

        Mockito.when(this.roleRepository.findByNameAndActive(RoleEnum.USER.getValue(), true))
                .thenReturn(Optional.empty());
        Mockito.when(this.mapper.toUserEntity(toSaveUserDomainObject)).thenReturn(toSaveUserEntity);
        Mockito.when(this.userRepository.save(toSaveUserEntity)).thenReturn(savedUserEntity);
        Mockito.when(this.mapper.toDomainObject(savedUserEntity)).thenReturn(savedUserDomainObject);

        Throwable exception = Assertions.catchThrowable(() -> this.userAdapter.save(toSaveUserDomainObject));

        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Role not found!");
        Mockito.verify(this.roleRepository, Mockito.times(1))
                .findByNameAndActive(RoleEnum.USER.getValue(), true);
    }

    @Test
    @DisplayName("Should return user domain object on save success")
    void shouldReturnUserDomainObjectOnSuccess() {
        User toSaveUserDomainObject = Mocks.userDomainObjectWithoutIdFactory();
        UserEntity toSaveUserEntity = Mocks.fromUserToUserEntityFactory(toSaveUserDomainObject);

        UserEntity savedUserEntity = toSaveUserEntity;
        savedUserEntity.setId(UUID.randomUUID());
        User savedUserDomainObject = Mocks.fromUserEntityToUserFactory(savedUserEntity);
        RoleEntity savedRoleEntity = Mocks.savedRoleEntityFactory();
        toSaveUserEntity.setRoles(List.of(savedRoleEntity));

        Mockito.when(this.roleRepository.findByNameAndActive(RoleEnum.USER.getValue(), true))
                .thenReturn(Optional.of(savedRoleEntity));
        Mockito.when(this.mapper.toUserEntity(toSaveUserDomainObject)).thenReturn(toSaveUserEntity);
        Mockito.when(this.userRepository.save(toSaveUserEntity)).thenReturn(savedUserEntity);
        Mockito.when(this.mapper.toDomainObject(savedUserEntity)).thenReturn(savedUserDomainObject);

        User userDomainObjectResult = this.userAdapter.save(toSaveUserDomainObject);

        Assertions.assertThat(userDomainObjectResult).isEqualTo(savedUserDomainObject);
        Mockito.verify(this.roleRepository, Mockito.times(1))
                .findByNameAndActive(RoleEnum.USER.getValue(), true);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(toSaveUserEntity);
        Mockito.verify(this.mapper, Mockito.times(1)).toDomainObject(savedUserEntity);
    }

    @Test
    @DisplayName("Should return null if user does not exist by id")
    void shouldReturnNullIfUserDOesNotExistById() {
        UUID userId = UUID.randomUUID();

        Mockito.when(this.userRepository.findByIdAndActive(userId, true)).thenReturn(Optional.empty());

        User userDomainObject = this.userAdapter.findById(userId);

        Assertions.assertThat(userDomainObject).isNull();
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdAndActive(userId, true);
    }

    @Test
    @DisplayName("Should return user on find by id success")
    void shouldReturnValidationUserOnFindByIdSuccess() {
        UserEntity savedUserEntity = Mocks.savedUserEntityFactory();
        User savedUserDomainObject = Mocks.fromUserEntityToUserFactory(savedUserEntity);

        Mockito.when(this.userRepository.findByIdAndActive(savedUserEntity.getId(), true))
                .thenReturn(Optional.of(savedUserEntity));
        Mockito.when(this.mapper.toDomainObject(savedUserEntity)).thenReturn(savedUserDomainObject);

        User userDomainObject = this.userAdapter.findById(savedUserEntity.getId());

        Assertions.assertThat(userDomainObject).isEqualTo(savedUserDomainObject);
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findByIdAndActive(savedUserEntity.getId(), true);
        Mockito.verify(this.mapper, Mockito.times(1))
                .toDomainObject(savedUserEntity);
    }

    @Test
    @DisplayName("Should update user data")
    void shouldReturnUserData() {
        User toSaveUserDomainObject = Mocks.savedUserDomainObjectFactory();
        UserEntity toUpdatedUserEntity = Mocks.fromUserToUserEntityFactory(toSaveUserDomainObject);

        UserEntity savedUserEntity = toUpdatedUserEntity;
        savedUserEntity.setId(toUpdatedUserEntity.getId());
        savedUserEntity.setPassword(UUID.randomUUID().toString());

        Mockito.when(this.userRepository.findByIdAndActive(toUpdatedUserEntity.getId(), true))
                .thenReturn(Optional.of(savedUserEntity));

        this.userAdapter.updatePassword(toSaveUserDomainObject.getUserId(), toSaveUserDomainObject.getPassword());

        Mockito.verify(this.userRepository, Mockito.times(1))
                .findByIdAndActive(toUpdatedUserEntity.getId(), true);
        Mockito.verify(this.userRepository, Mockito.times(1))
                .save(toUpdatedUserEntity);
    }
}
