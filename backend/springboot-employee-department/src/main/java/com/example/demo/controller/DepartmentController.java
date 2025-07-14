package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public Department create(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    @GetMapping
    public List<Department> getAll() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable String id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable String id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        departmentService.deleteDepartment(id);
    }
}
