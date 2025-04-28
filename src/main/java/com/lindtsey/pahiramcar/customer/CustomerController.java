package com.lindtsey.pahiramcar.customer;

import com.lindtsey.pahiramcar.car.Car;
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
@Tag(name = "Customer")
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

    @Operation(
            summary = "Uploads one image for a specific customer."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class))
    )
    @PostMapping("/api/customer/{customer-id}/customer-image")
    public ResponseEntity<?> saveCustomerImage(@PathVariable("customer-id") Integer customerId, @RequestPart("image") MultipartFile[] multipartFiles) throws IOException {
        Customer customer = this.customerService.saveCustomerImage(customerId, multipartFiles);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    // Getting all of the customers
    @Operation(
            summary = "Retrieves a list of all customers in the system."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                   array = @ArraySchema(schema = @Schema(implementation = Customer.class)))
    )
    @GetMapping("/api/customers")
    public ResponseEntity<?> findAllCustomers() {

        List<Customer> customers = this.customerService.findAll();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //Edit Customer
    @Operation(
            summary = "Updates the details of a specific customer except the password."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class))
    )
    @PutMapping("/api/customer/{customer-id}/edit")
    public ResponseEntity<?> saveCustomerEdits(@PathVariable("customer-id") Integer customerId,
                                               @Valid @RequestBody CustomerEditDTO dto) {
        Customer customer = customerService.saveCustomerEdit(customerId, dto);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    //Edit Customer Password
    @Operation(
            description = "Returns 200 when the password is updated successfully.",
            summary = "Updates the password of a specific customer."
    )
    @PatchMapping("/api/customer/{customer-id}/edit/password")
    public ResponseEntity<?> saveCustomerEdits(@PathVariable("customer-id") Integer customerId,
                                               @Valid @RequestBody CustomerPasswordEditDTO dto) {
        customerService.saveCustomerPassword(customerId, dto);

        return new ResponseEntity<>("Password is successfully changed.", HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves the details of a specific customer based on their ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Customer.class))
    )
    //Return a customer based on its id
    @GetMapping("/api/customers/{customer-id}")
    public ResponseEntity<?> findCustomerById(@PathVariable("customer-id") Integer customerId) {

        // Return a customer based on its id
        // Or null (empty Student Object) if no customer have that id
        CustomerResponseDTO dto = this.customerService.findCustomerById(customerId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //Delete a customer
    @Hidden
    @DeleteMapping("/api/customers/{customer-id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customer-id") Integer customerId) {
        this.customerService.deleteCustomerById(customerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
