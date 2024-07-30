package com.turinghealth.turing.health.entity.meta.product;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;


    // =========== RELATIONAL ===============


    // CHILDREN
//    @OneToMany
//    @JsonIgnore
//    private List<OrderItem> orderItems;
}
