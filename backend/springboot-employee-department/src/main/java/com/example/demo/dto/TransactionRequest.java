package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;

public class TransactionRequest {
    private Department department;
    private Employee employee;

    // Getters and Setters

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
