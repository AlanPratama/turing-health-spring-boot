package com.turinghealth.turing.health.utils.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {
    @NotBlank(message = "Category Name Cannot Be Null!")
    private String category;
}
