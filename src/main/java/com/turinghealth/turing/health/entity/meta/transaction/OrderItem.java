package com.turinghealth.turing.health.entity.meta.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turinghealth.turing.health.entity.meta.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;


    // ======= RELATIONAL ===================
    @ManyToOne(cascade = CascadeType.REMOVE)
    private OrderDetail orderDetail;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "productId")
    private Product product;

}
