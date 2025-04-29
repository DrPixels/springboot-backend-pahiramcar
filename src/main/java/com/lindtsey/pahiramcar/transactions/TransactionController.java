package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.reservations.Reservation;
import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransactionDTO;
import com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee.LateReturnFeeTransactionDTO;
import com.lindtsey.pahiramcar.utils.sorter.TransactionSorter;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Tag(name = "Transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
//    @PostMapping("/api/transactions")
//    public ResponseEntity<?> saveTransaction(@RequestBody TransactionDTO dto) {
//        Transaction transaction = transactionService.saveTransactionFromBooking(dto);
//
//        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
//    }

    @Operation(
            description = "Returns the transactions based on their transaction date. Most recent transactions will come first",
            summary = "Retrieves a list of all transactions."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))
    )
    @GetMapping("/api/admin/transactions")
    public ResponseEntity<?> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();

        TransactionSorter.mergeSortTransactions(transactions);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


    // Get all the transactions for customer
    @Operation(
            summary = "Retrieves a list of all transactions associated with a specific customer. Most recent on will go first."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))
    )
    @GetMapping("/api/customer/{customer-id}/transactions")
    public ResponseEntity<?> findCustomerTransactionsByUserId(@PathVariable("customer-id") Integer customerId) {
        List<Transaction> transactions = transactionService.findCustomerTransactionsByUserId(customerId);

        TransactionSorter.mergeSortTransactions(transactions);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves details of a specific transaction based on its ID."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Transaction.class))
    )
    @GetMapping("/api/transactions/{transaction-id}")
    public ResponseEntity<?> findTransactionById (@PathVariable("transaction-id") Integer transactionId) {
        Optional<Transaction> transaction = transactionService.findTransactionById(transactionId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Operation(
            description = "Returns a map containing the penalty fee amount for the given booking ID",
            summary = "Calculates the penalty fee for a late return associated with a specific booking."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(
                            type = "object"
                    )
    ))
    @GetMapping("/api/employee/booking/{booking-id}/transactions/penalty")
    public ResponseEntity<?> getPenalty (@PathVariable("booking-id") Integer bookingId) {

        Map<String, Double> result = transactionService.getLateReturnFee(bookingId);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(
            summary = "Creates a new transaction to record a penalty fee for a late return."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Transaction.class))
    )
    @PostMapping("/api/employee/booking/{booking-id}/transactions/penalty")
    public ResponseEntity<?> saveTransactionDueToPenalty (@PathVariable("booking-id") Integer bookingId, 
                                                          @RequestBody LateReturnFeeTransactionDTO dto) {

        Transaction transaction = transactionService.saveTransactionDueToPenalty(bookingId, dto);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Creates a new transaction to record a damage repair fee for a car, along with associated images."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Transaction.class))
    )
    @PostMapping("/api/employee/booking/{booking-id}/transactions/car-damage")
    public ResponseEntity<?> saveTransactionDueToCarDamage (@PathVariable("booking-id") Integer bookingId,
                                                              @RequestPart("transaction") DamageRepairFeeTransactionDTO dto,
                                                              @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Transaction transaction = transactionService.saveTransactionDueToCarDamage(bookingId, dto, multipartFiles);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @Hidden
    @DeleteMapping("/api/admin/transactions/{transaction-id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transaction-id") Integer transactionId) {
        transactionService.delete(transactionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
