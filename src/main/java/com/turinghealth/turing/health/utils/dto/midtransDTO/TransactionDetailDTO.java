package com.turinghealth.turing.health.utils.dto.midtransDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailDTO {

    @NotBlank(message = "Order ID Is Required")
    private String order_id;

    @NotBlank(message = "Gross Amount Is Required")
    private Integer gross_amount;

}
