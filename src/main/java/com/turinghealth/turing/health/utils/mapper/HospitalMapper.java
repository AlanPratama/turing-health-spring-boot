package com.turinghealth.turing.health.utils.mapper;

import com.turinghealth.turing.health.entity.meta.Hospital;

import static com.turinghealth.turing.health.service.impl.HospitalServiceImpl.gmapUrl;

public class HospitalMapper {

    public static Hospital mapToHospital(Hospital hospital){
        return Hospital.builder()
                .name(hospital.getName())
                .address(hospital.getAddress())
                .region(hospital.getRegion())
                .phone(hospital.getPhone())
                .province(hospital.getProvince())
                .gmap(gmapUrl+hospital.getAddress())
                .build();
    }
}
