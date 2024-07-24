package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.ICryptoAdapter;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootTest
class CryptoAdapterTests {
    private ICryptoAdapter cryptoAdapter;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        this.cryptoAdapter = new CryptoAdapter(passwordEncoder);
    }

    @Test
    @DisplayName("Should return encoded password")
    void shouldReturnEncodedPassword() {
        String password = "any_password";
        String encodedPassword = UUID.randomUUID().toString();

        Mockito.when(this.passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = this.cryptoAdapter.encode(password);

        Assertions.assertThat(result).isEqualTo(encodedPassword);
        Mockito.verify(this.passwordEncoder, Mockito.times(1)).encode(password);
    }

    @Test
    @DisplayName("Should return validation token")
    void shouldReturnValidationToken() {
        String validationToken = this.cryptoAdapter.generateValidationToken();
        Assertions.assertThat(validationToken).isNotBlank();
        Assertions.assertThat(validationToken).isNotNull();
        Assertions.assertThat(validationToken).isNotEmpty();
    }

    @Test
    @DisplayName("Should return hashed string")
    void shouldReturnHashedString() {
        String input = Mocks.faker.lorem().word();
        String result = this.cryptoAdapter.hash(input);
        Assertions.assertThat(result).isNotBlank();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
    }
}
