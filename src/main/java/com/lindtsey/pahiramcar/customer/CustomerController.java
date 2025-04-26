package com.lindtsey.pahiramcar.customer;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Adding new entity
//    @PostMapping("/api/customers/register")
//    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerDTO dto) {
//
//        Customer savedCustomer = this.customerService.saveCustomer(dto);
//        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
//    }

    @PostMapping("/api/customer/{customer-id}/add-customer-image")
    public ResponseEntity<?> saveCustomerImage(@PathVariable("customer-id") Integer customerId, @RequestPart("image") MultipartFile[] multipartFiles) throws IOException {
        Customer customer = this.customerService.saveCustomerImage(customerId, multipartFiles);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // Getting all of the customers
    @GetMapping("/api/customers")
    public ResponseEntity<?> findAllCustomers() {

        List<Customer> customers = this.customerService.findAll();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //Return a customer based on its id
    @GetMapping("/api/customers/{customer-id}")
    public ResponseEntity<?> findCustomerById(@PathVariable("customer-id") Integer customerId) {

        // Return a customer based on its id
        // Or null (empty Student Object) if no customer have that id
        Customer customer = this.customerService.findCustomerById(customerId);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    //Delete a customer
    @DeleteMapping("/api/customers/{customer-id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customer-id") Integer customerId) {
        this.customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
