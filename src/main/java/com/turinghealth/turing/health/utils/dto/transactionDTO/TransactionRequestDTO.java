package com.turinghealth.turing.health.utils.dto.transactionDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionRequestDTO {

    @NotNull(message = "Please Choose Your Address!")
    private Integer addressId;

    private String message;

    @NotNull(message = "Please Choose Your Payment Type!")
    private String paymentType;

    @NotNull(message = "Please Choose Products!")
    private List<Integer> products;

}
