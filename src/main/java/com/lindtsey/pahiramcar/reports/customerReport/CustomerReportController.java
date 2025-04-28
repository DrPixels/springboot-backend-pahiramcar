package com.lindtsey.pahiramcar.reports.customerReport;

import com.lindtsey.pahiramcar.customer.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Target;

@RestController
@Tag(name = "Customer Report")
public class CustomerReportController {

    private CustomerReportService customerReportService;

    public CustomerReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }

    // Accessible by customer

    @Operation(
            summary = "Retrieves a detailed report for a specific customer based on their ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerReport.class))
    )
    @GetMapping("/api/customers/{customer_id}/report")
    public ResponseEntity<?> customerReport(@PathVariable("customer_id") Integer customerId) {

        CustomerReport customerReport = customerReportService.getCustomerReport(customerId);

        return new ResponseEntity<>(customerReport, HttpStatus.OK);
    }


}
