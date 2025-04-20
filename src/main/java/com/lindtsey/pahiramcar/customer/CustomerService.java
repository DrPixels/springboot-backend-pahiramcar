package com.lindtsey.pahiramcar.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(CustomerDTO dto) {
        Customer customer = toCustomer(dto);

        return customerRepository.save(customer);
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
