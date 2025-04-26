package com.lindtsey.pahiramcar.utils.exceptionHandlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class DataIntegrityViolationExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Extract the root cause (SQL exception)
        Throwable rootCause = ex.getRootCause();

        // Check if the exception contains "Duplicate entry"
        if (rootCause != null && rootCause.getMessage().contains("Duplicate entry")) {
            // Extract the duplicate value using regex
            String errorMessage = rootCause.getMessage();
            String duplicateValue = extractDuplicateValue(errorMessage);

            // Return a clear error message
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Duplicate value detected: '" + duplicateValue + "'. Please ensure the value is unique.");
        }

        // Generic error message for other integrity violations
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("A data integrity violation occurred: " + ex.getMessage());
    }

    private String extractDuplicateValue(String errorMessage) {
        // Example: "Duplicate entry '28' for key 'cars.UKfsvkvs266rjdnm1hb7noxemoh'"
        // Use regex to extract the value inside single quotes
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("'(.*?)'");
        java.util.regex.Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1); // Return the first match (e.g., '28')
        }
        return "unknown"; // Fallback if no match is found
    }

}
