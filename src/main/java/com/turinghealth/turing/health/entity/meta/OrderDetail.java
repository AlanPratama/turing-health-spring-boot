package com.turinghealth.turing.health.entity.meta;

import com.turinghealth.turing.health.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer paymentId;
    private Integer total;
    private Status status;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

//    @OneToMany
//    @JoinColumn(name = "orderDetailId")
//    private PaymentDetail paymentDetail;

}
