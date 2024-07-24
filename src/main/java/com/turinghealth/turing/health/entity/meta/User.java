package com.turinghealth.turing.health.entity.meta;

import com.turinghealth.turing.health.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    private String nik;
    private String phone;
    private String address;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private Role role;

    @ManyToOne
    private Region region;
}
// BUSSIAT