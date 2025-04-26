package com.lindtsey.pahiramcar.transactions;

import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransactionDTO;
import com.lindtsey.pahiramcar.transactions.childClass.lateReturnFee.LateReturnFeeTransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
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


    // Get all the transactions for customer
    @GetMapping("/api/customer/{customer-id}/transactions")
    public ResponseEntity<?> findCustomerTransactionsByUserId(@PathVariable("customer-id") Integer customerId) {
        List<Transaction> transactions = transactionService.findCustomerTransactionsByUserId(customerId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/api/transactions/{transaction-id}")
    public ResponseEntity<?> findTransactionById (@PathVariable("transaction-id") Integer transactionId) {
        Optional<Transaction> transaction = transactionService.findTransactionById(transactionId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("/api/admin/booking/{booking-id}/transactions/penalty")
    public ResponseEntity<?> getPenalty (@PathVariable("booking-id") Integer bookingId) {

        Map<String, Double> result = transactionService.getLateReturnFee(bookingId);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/api/admin/booking/{booking-id}/transactions/penalty")
    public ResponseEntity<?> saveTransactionDueToPenalty (@PathVariable("booking-id") Integer bookingId, @RequestBody LateReturnFeeTransactionDTO dto) {

        Transaction transaction = transactionService.saveTransactionDueToPenalty(bookingId, dto);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping("/api/admin/booking/{booking-id}/transactions/car-damage")
    public ResponseEntity<?> saveTransactionDueToCarDamage (@PathVariable("booking-id") Integer bookingId,
                                                              @RequestPart("transaction") DamageRepairFeeTransactionDTO dto,
                                                              @RequestPart("images") MultipartFile[] multipartFiles) throws IOException {

        Transaction transaction = transactionService.saveTransactionDueToCarDamage(bookingId, dto, multipartFiles);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/transactions/{transaction-id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transaction-id") Integer transactionId) {
        transactionService.delete(transactionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
