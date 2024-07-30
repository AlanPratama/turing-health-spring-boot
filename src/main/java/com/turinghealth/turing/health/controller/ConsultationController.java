package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.ConsultationService;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationRequestDTO;
import com.turinghealth.turing.health.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultations")
public class ConsultationController {
    private final ConsultationService consultationService;

    @PostMapping("/start")
    public ResponseEntity<?> startConsultation(ConsultationRequestDTO request) {
        return Response.renderJson(
                consultationService.startConsultation(request),
                "Consultation Created Successfully! Wait For Doctor To Accept Your Consultation!"
        );
    }

    @PostMapping("/accept-consultation")
    public ResponseEntity<?> acceptConsultation(ConsultationRequestDTO request) {
        return Response.renderJson(
                consultationService.startConsultation(request),
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
