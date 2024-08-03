package com.walletwise.domain.useCases.expenses;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.exceptions.UnexpectedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.OutputStream;
import java.util.UUID;

@SpringBootTest
class GenerateFixedExpensesReportTests {
    private GenerateFixedExpensesReport generateFixedExpensesReport;

    @MockBean
    private IExpenseAdapter expenseAdapter;

    @BeforeEach
    void setUp() {
        this.generateFixedExpensesReport = new GenerateFixedExpensesReport(expenseAdapter);
    }

    @Test
    @DisplayName("Should throw UnexpectedException")
    void shouldThrowUnexpectedException() throws Exception {
        UUID userId = UUID.randomUUID();
        OutputStream output = Mockito.mock(OutputStream.class);

        Mockito.doThrow(new UnexpectedException("Any exception.")).when(this.expenseAdapter)
                .generateFixedExpensesReport(userId, output);

        Throwable exception = Assertions
                .catchThrowable(() -> this.generateFixedExpensesReport.generate(userId, output));

        Assertions.assertThat(exception).isInstanceOf(UnexpectedException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Any exception.");
        Mockito.verify(this.expenseAdapter, Mockito.times(1))
                .generateFixedExpensesReport(userId, output);
    }

    @Test
    @DisplayName("Should call generate report")
    void shouldCallGenerateReport() throws Exception {
        UUID userId = UUID.randomUUID();
        OutputStream output = Mockito.mock(OutputStream.class);

        Mockito.doNothing().when(this.expenseAdapter).generateFixedExpensesReport(userId, output);

        this.generateFixedExpensesReport.generate(userId, output);

        Mockito.verify(this.expenseAdapter, Mockito.times(1))
                .generateFixedExpensesReport(userId, output);
    }
}
