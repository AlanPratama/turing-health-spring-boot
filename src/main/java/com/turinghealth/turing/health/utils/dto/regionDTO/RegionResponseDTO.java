package com.turinghealth.turing.health.utils.dto.regionDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegionResponseDTO {
    private Integer id;
    private String name;
}
