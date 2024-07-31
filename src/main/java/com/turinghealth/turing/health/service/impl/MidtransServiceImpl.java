package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.configuration.MidtransConfig;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.service.MidtransService;
import com.turinghealth.turing.health.utils.adviser.exception.ValidateException;
import com.turinghealth.turing.health.utils.dto.midtransDTO.MidtransRequestDTO;
import com.turinghealth.turing.health.utils.response.MidtransResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MidtransServiceImpl implements MidtransService {
    private static final String midtransEndpoint = "https://api.sandbox.midtrans.com/v2/charge";
    private final MidtransConfig midtransConfig;
    private final RestTemplate restTemplate;
    private final RestClient restClient;
    private final String midtransUrl = "https://api.sandbox.midtrans.com/v2/";
    private final HttpHeaders headers;

    @Override
    public MidtransResponse chargeTransaction(MidtransRequestDTO midtransRequest) {
        HttpEntity<MidtransRequestDTO> request = new HttpEntity<>(midtransRequest, midtransConfig.httpHeaders());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                midtransEndpoint,
                HttpMethod.POST,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }

    @Override
    public MidtransResponse fetchTransaction(String order_id) {
        try {
            var midtransResponseDto = restClient.get()
                    .uri(midtransUrl + order_id + "/status")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .body(MidtransResponse.class);

            assert midtransResponseDto != null;
            return midtransResponseDto;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateTransactionStatusToCanceled(String orderId) {
        MidtransResponse midtransResponse = this.fetchTransaction(orderId);
        if (Objects.equals(midtransResponse.getTransactionStatus(), "pending")) {

            HttpEntity<MidtransRequestDTO> request = new HttpEntity<>(null, midtransConfig.httpHeaders());

            ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                    "https://api.sandbox.midtrans.com/v2/" + orderId + "/cancel",
                    HttpMethod.POST,
                    request,
                    MidtransResponse.class
            );

            return;

//            MidtransResponse canceledTransaction = restClient.post()
//                    .uri("https://api.sandbox.midtrans.com/v2/" + orderId + "/cancel")
//                    .headers(httpHeaders -> headers.addAll(midtransConfig.httpHeaders()))
//                    .retrieve()
//                    .body(MidtransResponse.class);
        }

        throw new ValidateException("Invalid Transaction Status!");
    }
}
