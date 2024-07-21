package com.walletwise.infra.adapters;

import com.github.javafaker.Faker;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@SpringBootTest
public class LoadUserAdapterTests {
    private final Faker faker = new Faker();
    private LoadUserAdapter loasLoadUserAdapter;
    @MockBean
    private IUserRepository userRepository;

    @BeforeEach
    void setup() {
        this.loasLoadUserAdapter = new LoadUserAdapter(userRepository);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException if user does not exist")
    void shouldReturnUsernameNotFoundExceptionIfUserDoesNotExist() {
        String username = this.faker.name().username();

        Mockito.when(this.userRepository.findByUsernameAndActive(username, true))
                .thenReturn(Optional.empty());

        Throwable exception = Assertions.catchThrowable(() -> this.loasLoadUserAdapter.loadUserByUsername(username));

        Assertions.assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findByUsernameAndActive(username, true);
    }

    @Test
    @DisplayName("Should return user details")
    void shouldReturnUserDetails() {
        String username = this.faker.name().username();

        UserEntity userEntity = Mocks.savedUserEntityFactory();

        Mockito.when(this.userRepository.findByUsernameAndActive(username, true))
                .thenReturn(Optional.of(userEntity));

        UserDetails result = this.loasLoadUserAdapter.loadUserByUsername(username);

        Assertions.assertThat(result.getUsername()).isEqualTo(userEntity.getUsername());
        Mockito.verify(this.userRepository, Mockito.times(1))
                .findByUsernameAndActive(username, true);
    }
}
