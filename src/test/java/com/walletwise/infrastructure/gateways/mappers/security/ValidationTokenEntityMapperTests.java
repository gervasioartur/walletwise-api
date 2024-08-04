package com.walletwise.infrastructure.gateways.mappers.security;

import com.walletwise.domain.entities.models.security.ValidationToken;
import com.walletwise.infrastructure.persistence.entities.security.ValidationTokenEntity;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidationTokenEntityMapperTests {
    private final ValidationTokenEntityMapper mapper = new ValidationTokenEntityMapper();

    @Test
    @DisplayName("should return validation token entity")
    void shouldReturnToValidationTokenEntity() {
        ValidationToken validationTokenDomainObject = Mocks.validationTokenFactory();

        ValidationTokenEntity result = this.mapper.toValidationTokenEntity(validationTokenDomainObject);

        Assertions.assertThat(result.getId()).isEqualTo(validationTokenDomainObject.getId());
        Assertions.assertThat(result.getToken()).isEqualTo(validationTokenDomainObject.getToken());
        Assertions.assertThat(result.getExpirationDate()).isEqualTo(validationTokenDomainObject.getExpirationDate());
    }

    @Test
    @DisplayName("should return validation token domain object")
    void shouldReturnValidationTokenDomainObject() {
        ValidationTokenEntity validationTokenEntity = Mocks.validationTokenEntityFactory();

        ValidationToken result = this.mapper.toValidationTokenDomainObject(validationTokenEntity);

        Assertions.assertThat(result.getId()).isEqualTo(validationTokenEntity.getId());
        Assertions.assertThat(result.getToken()).isEqualTo(validationTokenEntity.getToken());
        Assertions.assertThat(result.getExpirationDate()).isEqualTo(validationTokenEntity.getExpirationDate());
    }
}
