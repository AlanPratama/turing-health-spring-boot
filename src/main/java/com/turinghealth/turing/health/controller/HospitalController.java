package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<?> getHospitals(){
        return ResponseEntity.ok(hospitalService.getHospitals());
    }
}
