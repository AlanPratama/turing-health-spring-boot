package com.turinghealth.turing.health.controller;


import com.turinghealth.turing.health.service.MidtransService;
import com.turinghealth.turing.health.utils.dto.midtransDTO.MidtransRequestDTO;
import com.turinghealth.turing.health.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/midtrans")
public class MidtransController {

    private final MidtransService midtransService;

    @PostMapping
    public ResponseEntity<?> bebas(@RequestBody MidtransRequestDTO midtransRequest) {
        return ResponseEntity.ok(midtransService.chargeTransaction(midtransRequest));
    }

    @GetMapping("{orderId}")
    public ResponseEntity<?> getTransaction(@PathVariable String orderId) {
        return Response.renderJson(
                midtransService.fetchTransaction(orderId),
                "Transaction Fethed Successfully!"
        );
    }

}
