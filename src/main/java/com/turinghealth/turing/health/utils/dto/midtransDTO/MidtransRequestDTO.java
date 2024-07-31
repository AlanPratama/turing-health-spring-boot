package com.turinghealth.turing.health.utils.dto.midtransDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MidtransRequestDTO {

    private String payment_type;
    private TransactionDetailDTO transaction_details;
    private BankTransferDTO bank_transfer;

}
