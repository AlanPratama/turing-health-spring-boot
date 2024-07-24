package com.turinghealth.turing.health.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turinghealth.turing.health.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/seeding")
    public ResponseEntity<?> hospitalSeeder() throws JsonProcessingException {
        hospitalService.hospitalSeeder();
        return  ResponseEntity.status(HttpStatus.GONE).body("Seeded");
    }
}
