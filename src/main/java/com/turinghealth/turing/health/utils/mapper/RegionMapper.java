package com.turinghealth.turing.health.utils.mapper;

import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionResponseDTO;

public class RegionMapper {
    public static RegionResponseDTO regionResponseDTO(Region region) {
        return RegionResponseDTO.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
