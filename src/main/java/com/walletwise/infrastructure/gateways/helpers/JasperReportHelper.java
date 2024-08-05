package com.walletwise.infrastructure.gateways.helpers;

import com.walletwise.domain.entities.enums.GeneralEnumText;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class JasperReportHelper {
    private final DataSource dataSource;

    public JasperReportHelper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JasperPrint exportPDF(String reportName, Map<String, Object> parameters)
            throws IOException, JRException, SQLException {
        String path = GeneralEnumText.REPORT_DIR.getValue() + reportName + ".jasper";
        Resource resource = new ClassPathResource(path);
        try (InputStream inputStream = resource.getInputStream()) {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            Connection connection = this.dataSource.getConnection();
            parameters.put("SUBREPORT_DIR", GeneralEnumText.REPORT_DIR.getValue());
            return JasperFillManager.fillReport(jasperReport, parameters, connection);
        }
    }

}