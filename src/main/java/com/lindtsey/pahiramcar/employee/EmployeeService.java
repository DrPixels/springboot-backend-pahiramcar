package com.lindtsey.pahiramcar.employee;

import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ImageService imageService;

    public EmployeeService(EmployeeRepository employeeRepository, ImageService imageService) {
        this.employeeRepository = employeeRepository;
        this.imageService = imageService;
    }

    public Employee save(EmployeeDTO dto) {
        Employee employee = toEmployee(dto);

        return employeeRepository.save(employee);
    }

    public Employee saveEmployeeImage(Integer employeeId, MultipartFile[] multipartFiles) throws IOException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

        if(employee.getEmployeeImage() != null) {
            Image currentEmployeeImage = employee.getEmployeeImage();

            imageService.deleteImage(currentEmployeeImage.getImageId());
        }

        List<Image> savedImages = imageService.saveImages(multipartFiles, ImageOwnerType.EMPLOYEE, employeeId);

        employee.setEmployeeImage(savedImages.getFirst());

        return employee;
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
