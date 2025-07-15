package com.example.demo.service.impl;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public TransactionServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }
    private String generateEmployeeId() {
        long count = employeeRepository.count() + 1;
        return String.format("emp%03d", count); // emp001, emp002...
    }

    private String generateDepartmentId() {
        long count = departmentRepository.count() + 1;
        return String.format("dept%02d", count); // dept01, dept02...
    }

    @Override
    @Transactional
    public Employee createDepartmentAndEmployee(Department department, Employee employee) {
        department.setDepartmentId(generateDepartmentId());
        Department savedDept = departmentRepository.save(department);



        return employeeRepository.save(employee);
    }

}