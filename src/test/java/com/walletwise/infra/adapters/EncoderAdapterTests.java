package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IPasswordAdapter;
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
class EncoderAdapterTests {
    private IPasswordAdapter encoderAdapter;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        this.encoderAdapter = new PasswordAdapter(passwordEncoder);
    }

    @Test
    @DisplayName("Should return encoded password")
    void shouldReturnEncodedPassword() {
        String password = "any_password";
        String encodedPassword = UUID.randomUUID().toString();

        Mockito.when(this.passwordEncoder.encode(password)).thenReturn(encodedPassword);

        String result = this.encoderAdapter.encode(password);

        Assertions.assertThat(result).isEqualTo(encodedPassword);
        Mockito.verify(this.passwordEncoder, Mockito.times(1)).encode(password);
    }
}
