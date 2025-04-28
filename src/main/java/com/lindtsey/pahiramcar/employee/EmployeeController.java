package com.lindtsey.pahiramcar.employee;

import com.lindtsey.pahiramcar.customer.Customer;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Retrieves a list of all employees in the system."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Employee.class)))
    )
    @GetMapping("/api/employees")
    public ResponseEntity<?> findAllEmployees() {
        List<Employee> employees = this.employeeService.findAllEmployees();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves the details of a specific employee based on their ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Employee.class))
    )
    @GetMapping("/api/employees/{employee-id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable("employee-id") Integer employeeId) {
        Employee employee = this.employeeService.findEmployeeById(employeeId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(
            summary = "Registers a new employee in the system."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Employee.class))
    )
    @PostMapping("/api/admin/register-new-employee")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeDTO dto) {
        EmployeeResponseDTO savedEmployee = this.employeeService.save(dto);

        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Uploads one or more images for a specific employee."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Employee.class))
    )
    // Employee/Administrator
    @PostMapping("/api/employee/{employee-id}/add-employee-image")
    public ResponseEntity<?> saveCustomerImage(@PathVariable("employee-id") Integer employeeId, @RequestPart("image") MultipartFile[] multipartFiles) throws IOException {
        Employee employee = this.employeeService.saveEmployeeImage(employeeId, multipartFiles);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }


    // Administrator Deleting Employee
    @Hidden
    @DeleteMapping("/api/admin/employees/{employee-id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employee-id") Integer employeeId) {
        this.employeeService.delete(employeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
