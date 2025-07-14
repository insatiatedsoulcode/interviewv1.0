package com.example.demo.service.impl;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Department updateDepartment(String id, Department department) {
        Department existing = departmentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(department.getName());
            existing.setLocation(department.getLocation());
            return departmentRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }
}
