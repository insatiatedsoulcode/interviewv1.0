package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.model.Department;

public interface TransactionService {
   // Employee createEmployeeWithDepartment(Department department, Employee employee);
    Employee createDepartmentAndEmployee(Department department, Employee employee);

}
