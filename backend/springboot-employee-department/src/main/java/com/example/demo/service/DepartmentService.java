package com.example.demo.service;

import com.example.demo.model.Department;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();
    Department getDepartmentById(String id);
    Department updateDepartment(String id, Department department);
    void deleteDepartment(String id);
}
