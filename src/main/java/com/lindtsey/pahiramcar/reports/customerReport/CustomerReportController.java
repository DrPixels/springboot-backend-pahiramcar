package com.lindtsey.pahiramcar.reports.customerReport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerReportController {

    private CustomerReportService customerReportService;

    public CustomerReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }

    // Accessible by customer
    @GetMapping("/api/customers/{customer_id}/report")
    public ResponseEntity<?> customerReport(@PathVariable("customer_id") Integer customerId) {

        CustomerReport customerReport = customerReportService.getCustomerReport(customerId);

        return new ResponseEntity<>(customerReport, HttpStatus.OK);
    }


}
