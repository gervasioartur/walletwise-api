package com.walletwise.infra.gateways.mappers;

import com.walletwise.application.http.SignupRequest;
import com.walletwise.domain.entities.models.User;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDTOMapperTests {
    private UserDTOMapper mapper;

    @BeforeEach
    void setup(){
        this.mapper = new UserDTOMapper();
    }

    @Test
    @DisplayName("Should return user domain object")
    void shouldReturnUserDomainObject(){
        SignupRequest request = Mocks.signupRequestToUserFactory();

        User result =  this.mapper.toUserDomainObject(request);

        Assertions.assertThat(result.getFirstname()).isEqualTo(request.firstname());
        Assertions.assertThat(result.getLastname()).isEqualTo(request.lastname());
        Assertions.assertThat(result.getUsername()).isEqualTo(request.username());
        Assertions.assertThat(result.getEmail()).isEqualTo(request.email());
        Assertions.assertThat(result.getPassword()).isEqualTo(request.password());
    }
}
