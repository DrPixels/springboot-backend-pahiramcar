package com.lindtsey.pahiramcar.employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(EmployeeDTO dto) {
        Employee employee = toEmployee(dto);

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

    public Employee toEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setUsername(dto.username());
        employee.setPassword(dto.password());
        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setMiddleName(dto.middleName());
        employee.setEmail(dto.email());
        employee.setMobilePhone(dto.mobilePhone());
        employee.setBirthDate(dto.birthDate());

        return employee;
    }
}
