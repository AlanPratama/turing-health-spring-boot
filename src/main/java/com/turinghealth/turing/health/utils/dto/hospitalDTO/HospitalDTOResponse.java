package com.turinghealth.turing.health.utils.dto.hospitalDTO;

import com.turinghealth.turing.health.entity.meta.Region;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HospitalDTOResponse {
    private Integer id;
    private String name;
    private String address;
    private String region;
    private String phone;
    private String province;
    private String gmap;
}
