package com.turinghealth.turing.health.utils.dto.productDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    @NotBlank(message = "Name Cannot Be Null!")
    private String name;

    @NotNull(message = "Price Cannot Be Null!")
    private Integer price;

    @NotBlank(message = "Description Cannot Be Null!")
    private String description;

    @NotNull(message = "Category Cannot Be Null!")
    private Integer categoryId;

    @NotNull(message = "Category Cannot Be Null!")
    private Boolean available;
}
