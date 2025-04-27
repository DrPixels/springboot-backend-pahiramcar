package com.lindtsey.pahiramcar.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/api/employees")
    public ResponseEntity<?> findAllEmployees() {
        List<Employee> employees = this.employeeService.findAllEmployees();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/api/employees/{employee-id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable("employee-id") Integer employeeId) {
        Employee employee = this.employeeService.findEmployeeById(employeeId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/api/admin/register-new-employee")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeDTO dto) {
        EmployeeResponseDTO savedEmployee = this.employeeService.save(dto);

        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Employee/Administrator
    @PostMapping("/api/employee/{employee-id}/add-employee-image")
    public ResponseEntity<?> saveCustomerImage(@PathVariable("employee-id") Integer employeeId, @RequestPart("image") MultipartFile[] multipartFiles) throws IOException {
        Employee employee = this.employeeService.saveEmployeeImage(employeeId, multipartFiles);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    // Administrator Deleting Employee
    @DeleteMapping("/api/admin/employees/{employee-id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employee-id") Integer employeeId) {
        this.employeeService.delete(employeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
