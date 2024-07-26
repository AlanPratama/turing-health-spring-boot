package com.turinghealth.turing.health.utils.dto.hospitalDTO;

import com.turinghealth.turing.health.entity.meta.Region;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HospitalResponseDTO {
    private Integer id;
    private String name;
    private String address;
    private Region region;
    private String phone;
    private String province;
    private String gmap;
}
