package com.lindtsey.pahiramcar.transactions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/transactions")
    public ResponseEntity<?> findAllTransaction() {
        List<Transaction> transactions = transactionService.findAllTransactions();

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/api/transactions/{transaction-id}")
    public ResponseEntity<?> findTransactionById (@PathVariable("transaction-id") Integer transactionId) {
        Optional<Transaction> transaction = transactionService.findTransactionById(transactionId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping("/api/transactions/{transaction-id}/penalty")
    public ResponseEntity<?> updateTransactionDueToPenalty (@PathVariable("transaction-id") Integer transactionId, Map<String, Double> request) {

        Double penalty = request.get("penalty");

        transactionService.updateTransactionDueToPenalty(transactionId, penalty);

        return new ResponseEntity<>("Transaction has been updated.", HttpStatus.OK);
    }
    

    @DeleteMapping("/api/transactions/{transaction-id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transaction-id") Integer transactionId) {
        transactionService.delete(transactionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
