package com.lindtsey.pahiramcar.customer;

import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.images.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        if(customer.getCustomerImage() != null) {
            Image currentCustomerImage = customer.getCustomerImage();

            imageService.deleteImage(currentCustomerImage.getImageId());
        }

        List<Image> savedImages = imageService.saveImages(multipartFiles, ImageOwnerType.CUSTOMER, customerId);

        customer.setCustomerImage(savedImages.getFirst());

        return customer;
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
        customer.setUsername(dto.username());
        customer.setPassword(dto.password());
        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setMiddleName(dto.middleName());
        customer.setEmail(dto.email());
        customer.setMobilePhone(dto.mobilePhone());
        customer.setBirthDate(dto.birthDate());

        return customer;
    }

}
