package com.lindtsey.pahiramcar.employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public void delete(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
