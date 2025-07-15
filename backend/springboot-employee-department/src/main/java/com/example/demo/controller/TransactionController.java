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
    public ResponseEntity<Employee> createBothFromMap(@RequestBody Map<String, Object> request) {
        System.out.println("===== [POST] /api/transaction/creates called =====");
        ObjectMapper mapper = new ObjectMapper();
        Department department = mapper.convertValue(request.get("department"), Department.class);
        Employee employee = mapper.convertValue(request.get("employee"), Employee.class);
        System.out.println("[DEBUG] Converted Department: " + department);
        System.out.println("[DEBUG] Converted Employee: " + employee);

        Employee saved = transactionService.createDepartmentAndEmployee(department, employee);
        System.out.println("[DEBUG] Transaction saved: " + saved);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/create")
    public Employee createBoth(@RequestBody TransactionRequest request) {
        System.out.println("===== [POST] /api/transaction/create called =====");
        System.out.println("[DEBUG] TransactionRequest: " + request);

        Employee saved = transactionService.createDepartmentAndEmployee(
                request.getDepartment(), request.getEmployee()
        );

        System.out.println("[DEBUG] Transaction saved: " + saved);
        return saved;
    }
}
