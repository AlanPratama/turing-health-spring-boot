package com.turinghealth.turing.health.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalRequestDTO;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalService {
    void hospitalSeeder() throws JsonProcessingException;
    Hospital create (HospitalRequestDTO request);
    Page<HospitalResponseDTO> getAll(Pageable pageable, String name, String province, Integer regionId);
    Hospital getOne(Integer id);
    Hospital update(HospitalRequestDTO request, Integer id);
    void delete(Integer id);
}
