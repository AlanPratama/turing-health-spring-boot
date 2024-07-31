package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionRequestDTO;

public interface TransactionService {

    TransactionDTO create(TransactionRequestDTO request);
    TransactionDTO changeStatusToPacked(String orderId);
    TransactionDTO changeStatusToSent(String orderId, String resiCode);
    TransactionDTO changeStatusToAccepted(String orderId);


}
