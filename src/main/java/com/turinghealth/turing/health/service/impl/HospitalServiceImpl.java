package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.HospitalRepository;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.HospitalService;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalDTO;
import com.turinghealth.turing.health.utils.mapper.HospitalMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final RegionRepository regionRepository;
    private final RestClient restClient;
    private String baseUrl = "https://dekontaminasi.com/api/id/covid19/hospitals";
    public static String gmapUrl = "https://www.google.com/maps/place?q=";

    @Override
    public HospitalDTO getHospitals() {
//        String response = restClient.get()
//                .uri(UriComponentsBuilder.fromHttpUrl(baseUrl).toUriString(),
//                        UriBuilder::build).retrieve()
//                .body(String.class);

        RestTemplate restTemplate = new RestTemplate();
        Hospital[] hospitals = restTemplate.getForObject(baseUrl, Hospital[].class);
        List<?> hospitalList = Arrays.asList(hospitals);
        System.out.println(hospitalList);

//        if (hospitalDTO.getHospitalList().isEmpty()){
//            throw new RuntimeException("wut da hell");
//        }

//        List<Hospital> hospitals = hospitalDTO.getHospitalList();

//        for (var hos : hospitals){
//            Hospital hospital = HospitalMapper.mapToHospital(hos);
//            Region region = hospital.getRegion();
//
//            hospitalRepository.save(hospital);
//            regionRepository.save(region);
//        }

        return null;
    }
}
