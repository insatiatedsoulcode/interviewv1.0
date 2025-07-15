package com.example.demo.controller;

import com.example.demo.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/department/{deptId}")
    public ResponseEntity<byte[]> getEmployeeReportByDepartment(@PathVariable String deptId) {
        byte[] pdf = reportService.generateReportForDepartment(deptId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "employees-by-department.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
