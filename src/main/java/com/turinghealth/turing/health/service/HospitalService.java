package com.turinghealth.turing.health.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalDTOResponse;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HospitalService {
    void hospitalSeeder() throws JsonProcessingException;
    Hospital create (HospitalResponseDTO request);
    Page<HospitalDTOResponse> getAll(Pageable pageable, String name, String address, String Region);
    Hospital getOne(Integer id);
    Hospital update(Hospital request, Integer id);
    void delete(Integer id);
}
