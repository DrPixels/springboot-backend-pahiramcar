package com.lindtsey.pahiramcar.reports.adminReport;

import com.lindtsey.pahiramcar.reports.customerReport.CustomerReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class AdminReportController {

    private final AdminReportService adminReportService;

    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    // Accessible by customer
    @GetMapping("/api/admin/report")
    public ResponseEntity<?> adminReport() {

        AdminReport adminReport = adminReportService.getAdminReport();

        return new ResponseEntity<>(adminReport, HttpStatus.OK);
    }
    // Accessible by customer
    @GetMapping("/api/admin/custom-report")
    public ResponseEntity<?> customerReport(@RequestBody Map<String, LocalDateTime> request) {

        LocalDateTime startDateTime = request.get("startDateTime");
        LocalDateTime endDateTime = request.get("endDateTime");

        AdminCustomReport adminCustomReport = adminReportService.getAdminCustomReport(startDateTime, endDateTime);

        return new ResponseEntity<>(adminCustomReport, HttpStatus.OK);
    }

}
