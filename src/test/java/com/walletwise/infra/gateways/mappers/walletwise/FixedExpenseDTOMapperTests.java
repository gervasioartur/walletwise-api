package com.walletwise.infra.gateways.mappers.walletwise;

import com.walletwise.application.dto.walletwise.AddFixedExpenseRequest;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.util.UUID;

@SpringBootTest
class FixedExpenseDTOMapperTests {
    @Test
    @DisplayName("Should return fixed expense")
    void shouldReturnFixedExpense() {
        UUID userId = UUID.randomUUID();
        AddFixedExpenseRequest request = Mocks.addFixedExpenseRequest();

        FixedExpenseDTOMapper mapper = new FixedExpenseDTOMapper();
        FixedExpense result = mapper.toFixedExpenseDomainObj(userId, request);

        Assertions.assertThat(result.getUserId()).isEqualTo(userId);
        Assertions.assertThat(result.getDescription()).isEqualTo(request.description());
        Assertions.assertThat(result.getAmount()).isEqualTo(request.amount());
        Assertions.assertThat(result.getCategory()).isEqualTo(request.category());
        Assertions.assertThat(result.getDueDay()).isEqualTo(request.dueDay());
        Assertions.assertThat(result.getStartDate())
                .isEqualTo(request.startDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Assertions.assertThat(result.getEndDate())
                .isEqualTo(request.endDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Assertions.assertThat(result.getPaymentFrequency()).isEqualTo("MONTHLY");
    }
}
