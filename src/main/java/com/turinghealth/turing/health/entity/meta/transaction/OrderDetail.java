package com.turinghealth.turing.health.entity.meta.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turinghealth.turing.health.entity.enums.TransactionStatus;
import com.turinghealth.turing.health.entity.meta.OrderItem;
import com.turinghealth.turing.health.entity.meta.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;


    // ======= RELATIONAL ========================
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;


    // CHILDREN
    @OneToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OrderItem> orderItems;
}