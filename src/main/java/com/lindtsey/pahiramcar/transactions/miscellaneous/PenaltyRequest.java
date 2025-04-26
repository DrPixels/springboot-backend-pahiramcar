package com.lindtsey.pahiramcar.transactions.miscellaneous;

import com.lindtsey.pahiramcar.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyRequest {

    @NotNull(message = "Penalty paid amount is required.")
    private Double penaltyPaid;

    @NotNull(message = "Penalty payment mode is required.")
    private PaymentMode paymentMode;
}
