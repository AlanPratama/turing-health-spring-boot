package com.turinghealth.turing.health.entity.meta.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turinghealth.turing.health.entity.meta.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_user")
public class AddressUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String buyerPhone;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String posCode;

    @Column(nullable = false)
    private String addressDetail;

    private String fixPoint;

    @Column(nullable = false)
    private String type;

    private String message;


    // ====== RELATIONAL ==============
//    @ManyToOne(cascade = CascadeType.REMOVE)
    @ManyToOne
    @JsonIgnore
    private User user;

}
