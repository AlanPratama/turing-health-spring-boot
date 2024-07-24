package com.turinghealth.turing.health.utils.dto.hospitalDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.turinghealth.turing.health.entity.meta.Hospital;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class HospitalDTO<T> {
    @JsonProperty("")
    List<Hospital> hospitalList;
}