package com.turinghealth.turing.health.entity.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<User> users;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Hospital> hospitals;
}
