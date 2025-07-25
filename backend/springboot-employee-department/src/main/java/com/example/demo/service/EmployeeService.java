

package com.example.demo.service;

import com.example.demo.model.Employee;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String id);
    Employee updateEmployee(String id, Employee employee);
    void deleteEmployee(String id);
    List<Employee> getEmployeesByDepartmentId(String departmentId);


}