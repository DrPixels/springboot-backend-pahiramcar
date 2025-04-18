package com.lindtsey.pahiramcar.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

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

    @PostMapping("/api/employees")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = this.employeeService.save(employee);

        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/employees/{employee-id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employee-id") Integer employeeId) {
        this.employeeService.delete(employeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
