package com.turinghealth.turing.health.utils.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialistDTO {
    @NotBlank(message = "Specialist Name Cannot Be Blank!")
    private String name;
}
