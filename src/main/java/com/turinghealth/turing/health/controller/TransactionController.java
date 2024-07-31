package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.service.TransactionService;
import com.turinghealth.turing.health.utils.dto.transactionDTO.TransactionRequestDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create-transaction")
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody TransactionRequestDTO request, Errors errors) {
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while creating address", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                transactionService.create(request),
                "Transaction Created Successfully!"
        );
    }

    @PostMapping("/change-status/{orderId}")
    public ResponseEntity<?> changeStatus(@PathVariable String orderId, @RequestBody TransactionStatus status) {
        return Response.renderJson(
                transactionService.changeStatus(orderId, status),
                "Transaction Status Changed Successfully!"
        );
    }

}
