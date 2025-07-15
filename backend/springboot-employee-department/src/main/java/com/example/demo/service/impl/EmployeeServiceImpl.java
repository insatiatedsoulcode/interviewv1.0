package com.example.demo.service.impl;

import com.example.demo.model.Employee;
import com.example.demo.model.Department;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            System.out.println("❗ Employee already exists with ID: " + employee.getId());
            return null;
        }

        Department dept = departmentRepository.findById(employee.getDepartment().getId()).orElse(null);
        if (dept == null) {
            System.out.println("❌ Department not found for ID: " + employee.getDepartment().getId());
            return null;
        }

        employee.setDepartment(dept);
        Employee saved = employeeRepository.save(employee);
        System.out.println("✅ Saved employee: " + saved);
        return saved;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(String departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(String id, Employee updated) {
        Employee existing = employeeRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPosition(updated.getPosition());
        existing.setSalary(updated.getSalary());

        Department dept = departmentRepository.findById(updated.getDepartment().getId()).orElse(null);
        existing.setDepartment(dept);

        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
