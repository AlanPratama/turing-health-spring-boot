package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.regionDTO.RegionRequestDTO;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegionService {
    RegionResponseDTO create(RegionRequestDTO request);
    Page<RegionResponseDTO> getAll(Pageable pageable, String name);
    RegionResponseDTO getOne(Integer id);
    RegionResponseDTO update(RegionRequestDTO request, Integer id);
    void delete(Integer id);
}
