package com.turinghealth.turing.health.utils.dto.hospitalDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
import com.turinghealth.turing.health.entity.meta.Hospital;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
//    @JsonProperty("name")
    private String name;

//    @JsonProperty("address")
    private String address;

//    @JsonProperty("region")
    private String region;

//    @JsonProperty("phone")
    private String phone;

//    @JsonProperty("province")
    private String province;
}