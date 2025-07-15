package com.example.demo.service.impl;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Department existingDept = null;
        if (department.getId() != null && !department.getId().isBlank()) {
            existingDept = departmentRepository.findById(department.getId()).orElse(null);
        }

        if (existingDept == null) {
            System.out.println("[INFO] Department not found, validating input for insert...");
            if (department.getName() == null || department.getName().isBlank()
                    || department.getLocation() == null || department.getLocation().isBlank()) {
                throw new IllegalArgumentException("Cannot insert department with missing name or location.");
            }

            // Auto-generate Department ID with deptXX format
            List<Department> allDepts = departmentRepository.findAll();
            int max = allDepts.stream()
                    .filter(d -> d.getId() != null && d.getId().startsWith("dept"))
                    .mapToInt(d -> {
                        try {
                            return Integer.parseInt(d.getId().replaceAll("[^0-9]", ""));
                        } catch (Exception ex) {
                            return 0;
                        }
                    })
                    .max().orElse(0);

            String generatedDeptId = String.format("dept%02d", max + 1);
            department.setId(generatedDeptId);
            System.out.println("[DEBUG] Auto-generated Department ID: " + generatedDeptId);

            System.out.println("[INFO] Inserting new department with ID: " + department.getId());
            existingDept = departmentRepository.save(department);
        } else {
            System.out.println("[DEBUG] Department found: " + existingDept.getName());

            if (department.getLocation() != null && !department.getLocation().equals(existingDept.getLocation())) {
                System.out.println("[WARNING] Location mismatch: Request='" + department.getLocation()
                        + "', DB='" + existingDept.getLocation() + "'");
                throw new IllegalArgumentException("Location mismatch for department ID: " + department.getId());
            }
        }

        // Use provided ID if present, otherwise auto-generate safely
        if (employee.getId() == null || employee.getId().isBlank()) {
            List<Employee> allEmployees = employeeRepository.findAll();
            int max = allEmployees.stream()
                    .mapToInt(e -> {
                        try {
                            return Integer.parseInt(e.getId().replaceAll("[^0-9]", ""));
                        } catch (Exception ex) {
                            return 0;
                        }
                    })
                    .max().orElse(0);
            String generatedId = String.format("emp%03d", max + 1);
            employee.setId(generatedId);
            System.out.println("[DEBUG] Auto-generated Employee ID: " + generatedId);
        } else {
            System.out.println("[DEBUG] Provided Employee ID: " + employee.getId());
            if (employeeRepository.existsById(employee.getId())) {
                throw new RuntimeException("Employee ID already exists: " + employee.getId());
            }
        }

        employee.setDepartment(existingDept);
        Employee savedEmployee = employeeRepository.save(employee);

        System.out.println("[DEBUG] Employee saved: " + savedEmployee);

        if (employee.getId().equals("empFail")) throw new RuntimeException("Simulated failure");

        System.out.println("[DEBUG] Transaction completed successfully with ID: " + savedEmployee.getId());

        return savedEmployee;
    }
}
