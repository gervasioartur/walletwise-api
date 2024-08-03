package com.walletwise.infra.gateways.helpers;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JasperReportHelper {
    private final DataSource dataSource;

    public JasperReportHelper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JasperPrint exportPDF(String reportName, Map<String, Object> parameters) throws IOException, JRException, SQLException {
        Resource resource = new ClassPathResource("templates/report/" + reportName);
        File file = resource.getFile();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
        Connection connection = this.dataSource.getConnection();
        return JasperFillManager.fillReport(jasperReport, parameters, connection);
    }
}