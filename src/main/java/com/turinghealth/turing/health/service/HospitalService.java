package com.turinghealth.turing.health.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalDTO;

import java.util.List;

public interface HospitalService {
    List<HospitalDTO> getHospitals() throws JsonProcessingException;
}
