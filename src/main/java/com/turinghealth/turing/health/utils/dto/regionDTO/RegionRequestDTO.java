package com.turinghealth.turing.health.utils.dto.regionDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionRequestDTO {
    @NotBlank(message = "Region name can't be null")
    private String name;
}
