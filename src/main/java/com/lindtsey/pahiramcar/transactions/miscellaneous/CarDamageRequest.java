package com.lindtsey.pahiramcar.transactions.miscellaneous;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageRequest {

    @NotNull(message = "Penalty paid amount is required.")
    private Double carDamagePaid;


    @NotNull(message = "Penalty payment method is required.")
    private PaymentMode paymentMode;

    @NotBlank(message = "Car damage description is required.")
    private String carDamageDescription;
}
