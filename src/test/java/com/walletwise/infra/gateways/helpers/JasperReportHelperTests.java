package com.walletwise.infra.gateways.helpers;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class JasperReportHelperTests {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private DatabaseMetaData metaData;
    private JasperReport jasperReport;
    private JasperReportHelper helper;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        this.connection = mock(Connection.class);
        this.statement = mock(Statement.class);
        this.resultSet = mock(ResultSet.class);
        this.metaData = mock(DatabaseMetaData.class);
        this.jasperReport = mock(JasperReport.class);
        this.dataSource = mock(DataSource.class);
        this.helper = new JasperReportHelper(dataSource);

    }

    @Test
    @DisplayName("Should return IOException ")
    void shouldReturnIOException() {
        String reportName = "any_name";
        Map<String, Object> params = new HashMap<>();
        params.put("test", "test");
        Throwable exception = Assertions.catchThrowable(() -> this.helper.exportPDF(reportName, params));
        Assertions.assertThat(exception).isInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("Should return SQLException ")
    void shouldReturnSQLException() throws SQLException {
        String reportName = "fixedExpense";
        Map<String, Object> params = new HashMap<>();
        params.put("SUBREPORT_DIR", "templates/report/");
        when(this.dataSource.getConnection()).thenThrow(SQLException.class);
        Throwable exception = Assertions.catchThrowable(() -> this.helper.exportPDF(reportName, params));
        Assertions.assertThat(exception).isInstanceOf(SQLException.class);
    }

    @Test
    @DisplayName("Should return JRException ")
    void shouldReturnJRException() throws SQLException {
        String reportName = "fixedExpense";
        Map<String, Object> params = new HashMap<>();

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(connection.getMetaData()).thenReturn(metaData);
        when(metaData.getDatabaseProductName()).thenReturn("H2");
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        try (MockedStatic<JRLoader> jrLoader = Mockito.mockStatic(JRLoader.class)) {
            jrLoader.when(() -> JRLoader.loadObject(Mockito.any(InputStream.class)))
                    .thenReturn(jasperReport);

            try (MockedStatic<JasperFillManager> manager = Mockito.mockStatic(JasperFillManager.class)) {
                manager.when(() -> JasperFillManager.fillReport(jasperReport, params, connection))
                        .thenThrow(JRException.class);
                Throwable exception = Assertions.catchThrowable(() -> this.helper.exportPDF(reportName, params));
                Assertions.assertThat(exception).isInstanceOf(JRException.class);

            }
        }

    }

    @Test
    @DisplayName("Should generate report ")
    void shouldGenerateReport() throws SQLException, JRException, IOException {
        String reportName = "fixedExpense";
        Map<String, Object> params = new HashMap<>();

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(connection.getMetaData()).thenReturn(metaData);
        when(metaData.getDatabaseProductName()).thenReturn("H2");
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        try (MockedStatic<JRLoader> jrLoader = Mockito.mockStatic(JRLoader.class)) {
            jrLoader.when(() -> JRLoader.loadObject(Mockito.any(InputStream.class)))
                    .thenReturn(jasperReport);

            JasperPrint jasperPrint = mock(JasperPrint.class);

            try (MockedStatic<JasperFillManager> manager = Mockito.mockStatic(JasperFillManager.class)) {
                manager.when(() -> JasperFillManager.fillReport(jasperReport, params, connection))
                        .thenReturn(jasperPrint);
                JasperPrint result = this.helper.exportPDF(reportName, params);
                Assertions.assertThat(result).isNotNull();

            }
        }
    }
}
