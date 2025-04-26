package com.lindtsey.pahiramcar.customer;

import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.enums.Role;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import com.lindtsey.pahiramcar.reports.Time;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ImageService imageService;

    public CustomerService(CustomerRepository customerRepository, ImageService imageService) {
        this.customerRepository = customerRepository;
        this.imageService = imageService;
    }

    public Customer saveCustomer(CustomerDTO dto) {
        Customer customer = toCustomer(dto);

        return customerRepository.save(customer);
    }

    @Transactional
    public Customer saveCustomerImage(Integer customerId, MultipartFile[] multipartFiles) throws IOException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Customer savedCustomer = null;

        if(customer.getCustomerImage() != null) {
            Image currentCustomerImage = customer.getCustomerImage();

            customer.setCustomerImage(null);
            savedCustomer = customerRepository.save(customer);

            imageService.deleteImage(currentCustomerImage.getImageId());
        }

        List<Image> savedImages = imageService.saveImages(multipartFiles, ImageOwnerType.CUSTOMER, customerId);

        assert savedCustomer != null;
        savedCustomer.setCustomerImage(savedImages.getFirst());

        return savedCustomer;
    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Integer customerId) {
        return customerRepository.findById(customerId).orElse(new Customer());
    }

    public void deleteCustomerById(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    public Customer toCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setRole(Role.CUSTOMER);
        customer.setUsername(dto.username());
        customer.setPassword(dto.password());
        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setMiddleName(dto.middleName());
        customer.setEmail(dto.email());
        customer.setMobilePhone(dto.mobilePhone());
        customer.setBirthDate(dto.birthDate());
        customer.setNationality(dto.nationality());
        customer.setMaritalStatus(dto.maritalStatus());
        return customer;
    }

    public int countTotalCustomer() {
        return (int) customerRepository.count();
    }

    public int countTotalCustomerBeforeThisMonth() {
        return customerRepository.countCustomerByCreatedAtBefore(Time.startOfThisMonth());
    }

    public int countTotalCustomerBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return customerRepository.countCustomersByCreatedAtBetween(startDateTime, endDateTime);
    }


}
