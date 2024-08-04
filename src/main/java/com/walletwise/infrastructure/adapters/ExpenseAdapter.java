package com.walletwise.infrastructure.adapters;

import com.walletwise.domain.adapters.IExpenseAdapter;
import com.walletwise.domain.entities.models.walletwise.FixedExpense;
import com.walletwise.infrastructure.gateways.helpers.JasperReportHelper;
import com.walletwise.infrastructure.gateways.mappers.walletwise.FixedExpenseEntityMapper;
import com.walletwise.infrastructure.persistence.entities.walletwise.FixedExpenseEntity;
import com.walletwise.infrastructure.persistence.repositories.walletwise.IFixedExpenseRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExpenseAdapter implements IExpenseAdapter {
    private final IFixedExpenseRepository fixedExpenseRepository;
    private final FixedExpenseEntityMapper fixedExpenseEntityMapper;
    private final JasperReportHelper jasperReportHelper;

    public ExpenseAdapter(IFixedExpenseRepository fixedExpenseRepository,
                          FixedExpenseEntityMapper fixedExpenseEntityMapper,
                          JasperReportHelper jasperReportHelper) {
        this.fixedExpenseRepository = fixedExpenseRepository;
        this.fixedExpenseEntityMapper = fixedExpenseEntityMapper;
        this.jasperReportHelper = jasperReportHelper;
    }

    @Override
    @CacheEvict(value = "fixed_expenses", key = "#request.userId")
    public FixedExpense add(FixedExpense request) {
        FixedExpenseEntity entity = this.fixedExpenseEntityMapper.toFixedExpenseEntity(request);
        entity.setActive(true);
        entity = this.fixedExpenseRepository.save(entity);
        return this.fixedExpenseEntityMapper.toFixedExpense(entity);
    }

    @Override
    @Cacheable(value = "fixed_expenses", key = "#userId")
    public List<FixedExpense> getByUserId(UUID userId) {
        List<FixedExpenseEntity> entityList = this.fixedExpenseRepository.findByUserId(userId);
        return this.fixedExpenseEntityMapper.toFixedExpenseList(entityList);
    }

    @Override
    public void generateFixedExpensesReport(UUID userId, OutputStream outputStream) throws Exception {
        String reportName = "fixedExpense";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId.toString());
        parameters.put("CURRENCY", "R$ (BRAZIL)");
        JasperPrint jasperPrint = this.jasperReportHelper.exportPDF(reportName, parameters);
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
