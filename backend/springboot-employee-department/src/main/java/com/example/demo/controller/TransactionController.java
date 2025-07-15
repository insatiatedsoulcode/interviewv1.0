package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.dto.TransactionRequest;
import com.example.demo.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/creates")
    public ResponseEntity<Employee> createBoth(@RequestBody Map<String, Object> request) {
        ObjectMapper mapper = new ObjectMapper();
        Department department = mapper.convertValue(request.get("department"), Department.class);
        Employee employee = mapper.convertValue(request.get("employee"), Employee.class);

        Employee saved = transactionService.createDepartmentAndEmployee(department, employee);
        return ResponseEntity.ok(saved);
    }
    @PostMapping("/create")
    public Employee createBoth(@RequestBody TransactionRequest request) {
        return transactionService.createDepartmentAndEmployee(request.getDepartment(), request.getEmployee());
    }

}
