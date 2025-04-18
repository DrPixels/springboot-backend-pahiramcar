package com.lindtsey.pahiramcar.transactions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @PostMapping("/api/transactions")
    public ResponseEntity<?> saveTransaction(@RequestBody TransactionDTO dto) {
        Transaction transaction = transactionService.save(dto);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/transactions/{transaction-id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transaction-id") Integer transactionId) {
        transactionService.delete(transactionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
