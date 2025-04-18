package com.lindtsey.pahiramcar.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
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

}
