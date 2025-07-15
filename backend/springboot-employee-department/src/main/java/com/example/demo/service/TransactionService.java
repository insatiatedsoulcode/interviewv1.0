package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.Department;

public interface TransactionService {
    Employee createDepartmentAndEmployee(Department department, Employee employee);
}
