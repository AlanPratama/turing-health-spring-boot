package com.turinghealth.turing.health.entity.meta;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    //@ManyToOne
    //private OrderDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

}
