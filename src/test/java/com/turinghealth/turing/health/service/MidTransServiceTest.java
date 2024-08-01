package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.configuration.MidtransConfig;
import com.turinghealth.turing.health.service.impl.MidtransServiceImpl;
import com.turinghealth.turing.health.utils.dto.midtransDTO.BankTransferDTO;
import com.turinghealth.turing.health.utils.dto.midtransDTO.MidtransRequestDTO;
import com.turinghealth.turing.health.utils.dto.midtransDTO.TransactionDetailDTO;
import com.turinghealth.turing.health.utils.response.MidtransResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MidTransServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestClient restClient;

    @InjectMocks
    private MidtransServiceImpl midtransService;

    private MidtransRequestDTO midtransRequestDTO;
    private MidtransResponse midtransResponse;
    private TransactionDetailDTO transactionDetailDTO;
    private BankTransferDTO bankTransferDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        transactionDetailDTO = new TransactionDetailDTO();
            transactionDetailDTO.setOrder_id("orderId");
            transactionDetailDTO.setGross_amount(1);

        bankTransferDTO = new BankTransferDTO();
            bankTransferDTO.setBank("bank");

        midtransRequestDTO = new MidtransRequestDTO();
            midtransRequestDTO.setPayment_type("bank_transfer");
            midtransRequestDTO.setTransaction_details(transactionDetailDTO);
            midtransRequestDTO.setBank_transfer(bankTransferDTO);

        midtransResponse = new MidtransResponse();
            midtransResponse.setStatusCode("statusCode");
            midtransResponse.setStatusMessage("statusMessage");
            midtransResponse.setTransactionId("transactionId");
            midtransResponse.setOrderId("orderId");
            midtransResponse.setGrossAmount("1");
            midtransResponse.setCurrency("IDR");
            midtransResponse.setPaymentType("bank_transfer");
            midtransResponse.setTransactionTime("transactionTime");
            midtransResponse.setTransactionStatus("transactionStatus");
            midtransResponse.setFraudStatus("fraudStatus");
            midtransResponse.setExpiryTime("expiryTime");
    }

    @Test
    public void chargeTransaction_Success() {
        HttpHeaders headers = MidtransConfig.httpHeaders();
        ResponseEntity<MidtransResponse> responseEntity = new ResponseEntity<>(midtransResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.sandbox.midtrans.com/v2/charge"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(MidtransResponse.class)
        )).thenReturn(responseEntity);

        MidtransResponse response = midtransService.chargeTransaction(midtransRequestDTO);

        assertNotNull(response);
    }

//    @Test
//    public void fetchTransaction_Success() {
//        String orderId = "orderId";
//        String uri = "https://api.sandbox.midtrans.com/v2/" + orderId + "/status";
//
//        HttpHeaders headers = MidtransConfig.httpHeaders();
//        when(restTemplate.exchange(
//                eq(uri),
//                eq(HttpMethod.GET),
//                any(HttpEntity.class),
//                eq(MidtransResponse.class)
//        )).thenReturn(new ResponseEntity<>(midtransResponse, HttpStatus.OK));
//
//        MidtransResponse response = midtransService.fetchTransaction(orderId);
//
//        assertNotNull(response);
//        assertEquals("transactionId", response.getTransactionId());
//        assertEquals("StatusMessage", response.getStatusMessage());
//    }
}
