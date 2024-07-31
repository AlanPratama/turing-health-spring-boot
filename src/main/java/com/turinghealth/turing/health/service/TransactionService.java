package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionRequestDTO;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionResiCodeDTO;

import java.util.List;

public interface TransactionService {

    TransactionDTO create(TransactionRequestDTO request);
    TransactionDTO changeStatusToCanceled(String orderId);
    TransactionDTO changeStatusToPacked(String orderId);
    TransactionDTO changeStatusToSent(String orderId, TransactionResiCodeDTO resiCode);
    TransactionDTO changeStatusToAccepted(String orderId);

    List<TransactionDTO> getAllTransaction();
    TransactionDTO getOneTransaction(String orderId);

}
