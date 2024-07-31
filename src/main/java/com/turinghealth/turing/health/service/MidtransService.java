package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.midtransDTO.MidtransRequestDTO;
import com.turinghealth.turing.health.utils.response.MidtransResponse;

public interface MidtransService {

    MidtransResponse chargeTransaction(MidtransRequestDTO midtransRequest);
    MidtransResponse fetchTransaction(String order_id);
    MidtransResponse updateTransactionStatus(String orderId, String status);

}
