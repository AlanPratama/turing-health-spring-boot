package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.ConsultationService;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationAcceptRequestDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationRequestDTO;
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
@RequestMapping("/api/consultations")
public class ConsultationController {
    private final ConsultationService consultationService;

    @PostMapping("/start")
    @Validated
    public ResponseEntity<?> startConsultation(@Valid @RequestBody ConsultationRequestDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Update User Failed!", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                consultationService.startConsultation(request),
                "Consultation Created Successfully! Wait For Doctor To Accept Your Consultation!"
        );
    }

    @PostMapping("/accept-consultation/{id}")
    @Validated
    public ResponseEntity<?> acceptConsultation(
            @Valid @RequestBody ConsultationAcceptRequestDTO request,
            Errors errors,
            @PathVariable Integer id
    ) {
        if (errors.hasErrors()) {
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Update User Failed!", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                consultationService.acceptConsultation(request, id),
                "Consultation Created Successfully! Wait For Doctor To Accept Your Consultation!"
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return Response.renderJson(
                consultationService.getAll(),
                "Consultation Fetched Successfully!"
        );
    }

}
