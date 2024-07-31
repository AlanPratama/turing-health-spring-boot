package com.turinghealth.turing.health.entity.meta.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turinghealth.turing.health.entity.enums.TransactionStatus;
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

    private String message;
    private String resiCode;
    private String paymentType;
    private String vaNumber;
    private String expiryTime;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;


    // ======= RELATIONAL ========================
    @ManyToOne
    private User user;

    @ManyToOne
    private AddressUser addressUser;

    // CHILDREN
    @OneToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OrderItem> orderItems;
}