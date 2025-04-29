package com.lindtsey.pahiramcar.reports.adminReport;

import com.lindtsey.pahiramcar.reports.customerReport.CustomerReport;
import com.lindtsey.pahiramcar.utils.shared.DateRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@RestController
@Tag(name = "Admin Report")
public class AdminReportController {

    private final AdminReportService adminReportService;

    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    // Accessible by customer
    @Operation(
            summary = "Retrieves an overall report for the administrator, summarizing key metrics or data across the system."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdminReport.class))
    )
    @GetMapping("/api/admin/report")
    public ResponseEntity<?> adminReport() {

        AdminReport adminReport = adminReportService.getAdminReport();

        return new ResponseEntity<>(adminReport, HttpStatus.OK);
    }
    // Accessible by customer
    @Operation(
            summary = "Retrieves a custom report for the administrator based on a specified date and time range."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AdminCustomReport.class))
    )
    @GetMapping("/api/admin/custom-report")
    public ResponseEntity<?> adminCustomReport(@RequestBody DateRange dateRange) {

        LocalDateTime startDateTime = dateRange.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = dateRange.getEndDate().atTime(LocalTime.MAX);
        AdminCustomReport adminCustomReport = adminReportService.getAdminCustomReport(startDateTime, endDateTime);

        return new ResponseEntity<>(adminCustomReport, HttpStatus.OK);
    }

}
