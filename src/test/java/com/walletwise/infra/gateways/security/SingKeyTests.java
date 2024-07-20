package com.walletwise.infra.gateways.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

@SpringBootTest
class SingKeyTests {
    @Autowired
    private SignKey signKey;

    @Test
    @DisplayName("Should create sign key successfully")
    void shouldCreateSignKeySuccessfully(){
        Key key =  signKey.getSignKey();
        Assertions.assertThat(key).isNotNull();
    }
}
