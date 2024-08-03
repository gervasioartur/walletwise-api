package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.FixedExpense;
import com.walletwise.infra.gateways.helpers.JasperReportHelper;
import com.walletwise.infra.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infra.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.infra.persistence.repositories.walletwise.IFixedExpenseRepository;
import com.walletwise.mocks.Mocks;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class ExpenseAdapterTests {
    private IExpenseAdapter expenseAdapter;
    @MockBean
    private IFixedExpenseRepository fixedExpenseRepository;
    @MockBean
    private FixedExpenseEntityMapper fixedExpenseEntityMapper;
    @MockBean
    private JasperReportHelper jasperReportHelper;

    @BeforeEach
    public void setUp() {
        expenseAdapter = new ExpenseAdapter(fixedExpenseRepository, fixedExpenseEntityMapper, jasperReportHelper);
    }

    @Test
    @DisplayName("Should add fixed expense")
    void shouldAddFixedExpense() {
        FixedExpense fixedExpense = Mocks.fixedExpenseWithNoIdFactory();
        FixedExpenseEntity fixedExpenseEntity = Mocks.formFixedExpenseToEntity(fixedExpense);

        FixedExpenseEntity savedFixedExpenseEntity = Mocks.formFixedExpenseToEntity(fixedExpense);
        savedFixedExpenseEntity.setId(UUID.randomUUID());

        Mockito.when(this.fixedExpenseEntityMapper.toFixedExpenseEntity(fixedExpense)).thenReturn(fixedExpenseEntity);
        Mockito.when(this.fixedExpenseRepository.save(fixedExpenseEntity)).thenReturn(savedFixedExpenseEntity);
        Mockito.when(this.fixedExpenseEntityMapper.toFixedExpense(savedFixedExpenseEntity)).thenReturn(fixedExpense);

        FixedExpense result = this.expenseAdapter.add(fixedExpense);

        Assertions.assertThat(result.getId()).isEqualTo(fixedExpense.getId());
        Assertions.assertThat(result.getUserId()).isEqualTo(fixedExpense.getUserId());
        Mockito.verify(this.fixedExpenseEntityMapper, Mockito.times(1)).toFixedExpenseEntity(fixedExpense);
        Mockito.verify(this.fixedExpenseRepository, Mockito.times(1)).save(fixedExpenseEntity);
        Mockito.verify(this.fixedExpenseEntityMapper, Mockito.times(1)).toFixedExpense(savedFixedExpenseEntity);
    }

    @Test
    @DisplayName("Should return a list of expenses on find by user id")
    void shouldReturnAListOfExpensesOnFIndByUserId() {
        UUID userId = UUID.randomUUID();
        List<FixedExpenseEntity> fiexExpenseEntityList = Mocks.fixedExpenseEntityListFactory(userId);
        List<FixedExpense> fixedExpenseList = Mocks.fixedExpenseListFactory(fiexExpenseEntityList);

        Mockito.when(this.fixedExpenseRepository.findByUserId(userId)).thenReturn(fiexExpenseEntityList);
        Mockito.when(this.fixedExpenseEntityMapper.toFixedExpenseList(fiexExpenseEntityList))
                .thenReturn(fixedExpenseList);

        List<FixedExpense> result = this.expenseAdapter.getByUserId(userId);

        Assertions.assertThat(result).isEqualTo(fixedExpenseList);
        Mockito.verify(this.fixedExpenseRepository, Mockito.times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should throw JRException ")
    void shouldThrowJRException() throws JRException, SQLException, IOException {
        UUID userId = UUID.randomUUID();
        OutputStream outputStream = Mockito.mock(OutputStream.class);

        String reportName = "fixedExpense.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$");

        Mockito.doThrow(new JRException("Any")).when(this.jasperReportHelper)
                .exportPDF(reportName, parameters);

        Throwable exception = Assertions.catchThrowable(() -> this.expenseAdapter
                .generateFixedExpensesReport(userId, outputStream));

        Assertions.assertThat(exception).isInstanceOf(JRException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Any");
        Mockito.verify(this.jasperReportHelper, Mockito.times(1))
                .exportPDF(reportName, parameters);
    }

    @Test
    @DisplayName("Should throw IOException ")
    void shouldThrowIOException() throws JRException, SQLException, IOException {
        UUID userId = UUID.randomUUID();
        OutputStream outputStream = Mockito.mock(OutputStream.class);

        String reportName = "fixedExpense.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$");

        Mockito.doThrow(new IOException("Any")).when(this.jasperReportHelper)
                .exportPDF(reportName, parameters);

        Throwable exception = Assertions.catchThrowable(() -> this.expenseAdapter
                .generateFixedExpensesReport(userId, outputStream));

        Assertions.assertThat(exception).isInstanceOf(IOException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Any");
        Mockito.verify(this.jasperReportHelper, Mockito.times(1))
                .exportPDF(reportName, parameters);
    }

    @Test
    @DisplayName("Should throw SQLException ")
    void shouldThrowSQLException() throws JRException, SQLException, IOException {
        UUID userId = UUID.randomUUID();
        OutputStream outputStream = Mockito.mock(OutputStream.class);

        String reportName = "fixedExpense.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$");

        Mockito.doThrow(new SQLException("Any")).when(this.jasperReportHelper)
                .exportPDF(reportName, parameters);

        Throwable exception = Assertions.catchThrowable(() -> this.expenseAdapter
                .generateFixedExpensesReport(userId, outputStream));

        Assertions.assertThat(exception).isInstanceOf(SQLException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo("Any");
        Mockito.verify(this.jasperReportHelper, Mockito.times(1))
                .exportPDF(reportName, parameters);
    }

    @Test
    @DisplayName("Should  generate report ")
    void shouldGenerateReport() throws Exception {
        UUID userId = UUID.randomUUID();
        OutputStream outputStream = Mockito.mock(OutputStream.class);

        String reportName = "fixedExpense.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$");

        Mockito.when(this.jasperReportHelper.exportPDF(reportName, parameters))
                .thenReturn(JasperPrint.class.newInstance());

        this.expenseAdapter.generateFixedExpensesReport(userId, outputStream);

        Mockito.verify(this.jasperReportHelper, Mockito.times(1))
                .exportPDF(reportName, parameters);
    }
}
