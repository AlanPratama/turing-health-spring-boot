package com.turinghealth.turing.health.entity.meta.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turinghealth.turing.health.entity.meta.transaction.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
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
