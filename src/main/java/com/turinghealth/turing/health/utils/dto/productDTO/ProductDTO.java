package com.turinghealth.turing.health.utils.dto.productDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String name;
    private Double price;
    private String description;
    private String imageLink;
    private Integer categoryId;

}
