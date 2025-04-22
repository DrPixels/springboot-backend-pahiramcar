package com.lindtsey.pahiramcar.utils;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalRestExceptionHandler {

    @ExceptionHandler(CarAlreadyReservedException.class)
    public ResponseEntity<String> handleCarAlreadyReserved(CarAlreadyReservedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
