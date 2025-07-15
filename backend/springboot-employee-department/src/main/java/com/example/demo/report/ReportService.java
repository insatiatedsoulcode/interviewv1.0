package com.example.demo.report;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public byte[] generateReportForDepartment(String deptId) {
        try {
            InputStream reportStream = this.getClass().getResourceAsStream("/reports/employees_by_department.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            Department dept = departmentRepository.findById(deptId).orElse(null);
            if (dept == null) return null;
            List<Employee> employees = dept.getEmployees();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("departmentName", dept.getName());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
