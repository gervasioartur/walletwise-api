package com.walletwise.infrastructure.adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.walletwise.FixedExpense;
import com.walletwise.infrastructure.gateways.helpers.JasperReportHelper;
import com.walletwise.infrastructure.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infrastructure.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.infrastructure.persistence.repositories.walletwise.IFixedExpenseRepository;
import com.walletwise.mocks.Mocks;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.PrintPageFormat;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        when(this.fixedExpenseEntityMapper.toFixedExpenseEntity(fixedExpense)).thenReturn(fixedExpenseEntity);
        when(this.fixedExpenseRepository.save(fixedExpenseEntity)).thenReturn(savedFixedExpenseEntity);
        when(this.fixedExpenseEntityMapper.toFixedExpense(savedFixedExpenseEntity)).thenReturn(fixedExpense);

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

        when(this.fixedExpenseRepository.findByUserId(userId)).thenReturn(fiexExpenseEntityList);
        when(this.fixedExpenseEntityMapper.toFixedExpenseList(fiexExpenseEntityList))
                .thenReturn(fixedExpenseList);

        List<FixedExpense> result = this.expenseAdapter.getByUserId(userId);

        Assertions.assertThat(result).isEqualTo(fixedExpenseList);
        Mockito.verify(this.fixedExpenseRepository, Mockito.times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should throw exception on generate fixed expenses report")
    void shouldThrowExceptionOnGenerateFixedExpenseReport() throws JRException, SQLException, IOException {
        UUID userId = UUID.randomUUID();
        String reportName = "fixedExpense";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$ (BRAZIL)");

        OutputStream outputStream = mock(OutputStream.class);

        when(this.jasperReportHelper.exportPDF(reportName, parameters))
                .thenReturn(null);

        Throwable exception = Assertions.catchThrowable(() -> this.expenseAdapter
                .generateFixedExpensesReport(userId, outputStream));

        Assertions.assertThat(exception).isInstanceOf(Exception.class);
        Mockito.verify(this.jasperReportHelper, Mockito.times(1))
                .exportPDF(reportName, parameters);
    }

    @Test
    @DisplayName("Should generate fixed expenses report")
    void shouldGenerateFixedExpenseReport() throws Exception {
        UUID userId = UUID.randomUUID();
        String reportName = "fixedExpense";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$ (BRAZIL)");

        OutputStream outputStream = mock(OutputStream.class);
        JasperPrint jasperPrint = mock(JasperPrint.class);
        PrintPageFormat printPageFormat = mock(PrintPageFormat.class);

        when(printPageFormat.getPageWidth()).thenReturn((int) 595.0);
        when(printPageFormat.getPageHeight()).thenReturn((int) 842.0);
        when(jasperPrint.getPageFormat(0)).thenReturn(printPageFormat);

        when(this.jasperReportHelper.exportPDF(reportName, parameters))
                .thenReturn(jasperPrint);

        this.expenseAdapter.generateFixedExpensesReport(userId, outputStream);

        Mockito.verify(this.jasperReportHelper, Mockito.times(1))
                .exportPDF(reportName, parameters);
    }
}
