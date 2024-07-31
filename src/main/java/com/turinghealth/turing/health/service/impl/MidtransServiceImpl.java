package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.configuration.MidtransConfig;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.service.MidtransService;
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
    public MidtransResponse updateTransactionStatus(String orderId, String status) {
        MidtransResponse midtransResponse = this.fetchTransaction(orderId);

        return midtransResponse;
    }
}
