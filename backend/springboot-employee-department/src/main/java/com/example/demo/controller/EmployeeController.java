package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        System.out.println("POST /api/employees - Creating employee: " + employee);
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        System.out.println("PUT /api/employees/" + id + " - Updating employee: " + employee);
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        System.out.println("DELETE /api/employees/" + id + " - Deleting employee");
        employeeService.deleteEmployee(id);
    }
    @GetMapping("/by-department/{deptId}")
    public List<Employee> getByDepartmentId(@PathVariable String deptId) {
        System.out.println("GET /api/employees/by-department/" + deptId + " - Fetching employees");
        return employeeService.getEmployeesByDepartmentId(deptId);
    }

}
