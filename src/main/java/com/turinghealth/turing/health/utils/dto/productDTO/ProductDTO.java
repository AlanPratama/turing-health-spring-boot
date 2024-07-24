package com.turinghealth.turing.health.utils.dto.productDTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String name;
    private Integer price;
    private String description;
    private String imageLink;
    private Integer categoryId;
}
