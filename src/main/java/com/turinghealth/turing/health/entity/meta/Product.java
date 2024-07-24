package com.turinghealth.turing.health.entity.meta;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer categoryId;
    private String imageLink;
    private String name;
    private Integer price;
    private String description;

    
    // BUSSIAT

}
