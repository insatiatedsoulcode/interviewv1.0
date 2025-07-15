package com.example.demo.service.impl;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public Employee createDepartmentAndEmployee(Department department, Employee employee) {
        System.out.println("[DEBUG] Starting transaction to create employee with department");

        if (department == null) {
            throw new IllegalArgumentException("Department object is null");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee object is null");
        }

        System.out.println("[DEBUG] Department ID: " + department.getId());

        Department existingDept = departmentRepository.findById(department.getId())
                .orElseThrow(() -> new RuntimeException("Department not found: " + department.getId()));

        System.out.println("[DEBUG] Department found: " + existingDept.getName());

        // Use provided ID if present, otherwise auto-generate
        if (employee.getId() == null || employee.getId().isBlank()) {
            long count = employeeRepository.count() + 1;
            String generatedId = String.format("emp%03d", count);
            employee.setId(generatedId);
            System.out.println("[DEBUG] Auto-generated Employee ID: " + generatedId);
        } else {
            System.out.println("[DEBUG] Provided Employee ID: " + employee.getId());
            // Check for duplicate ID
            if (employeeRepository.existsById(employee.getId())) {
                throw new RuntimeException("Employee ID already exists: " + employee.getId());
            }
        }

        employee.setDepartment(existingDept);
        Employee savedEmployee = employeeRepository.save(employee);

        System.out.println("[DEBUG] Employee saved: " + savedEmployee);

        // Uncomment to simulate rollback
        if (employee.getId().equals("empFail")) throw new RuntimeException("Simulated failure");

        System.out.println("[DEBUG] Transaction completed successfully with ID: " + savedEmployee.getId());

        return savedEmployee;
    }
}