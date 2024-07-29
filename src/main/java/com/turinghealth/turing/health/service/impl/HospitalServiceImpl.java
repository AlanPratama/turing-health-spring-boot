package com.turinghealth.turing.health.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.HospitalRepository;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.HospitalService;
import com.turinghealth.turing.health.service.RegionService;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalRequestDTO;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalResponseDTO;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionRequestDTO;
import com.turinghealth.turing.health.utils.specification.HospitalSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
        private final HospitalRepository hospitalRepository;
        private final RegionRepository regionRepository;
        private final RegionService regionService;
//        private final RestClient restClient;
        private final RestTemplate restTemplate;

        private String baseUrl = "https://dekontaminasi.com/api/id/covid19/hospitals";
        public static String gmapUrl = "https://www.google.com/maps/place?q=";
    
        @Override
        public void hospitalSeeder() throws JsonProcessingException {
            String response = restTemplate.getForObject(
                    UriComponentsBuilder.fromHttpUrl(baseUrl).toUriString(), String.class);

            try {
                ObjectMapper objectMapper2 = new ObjectMapper();
                JsonNode jsonNode = objectMapper2.readTree(response);

                if (jsonNode.isArray()) {
                    for (JsonNode hospitalNode : jsonNode) {
                        String name = hospitalNode.path("name").asText();
                        String address = hospitalNode.path("address").asText();
                        String region = hospitalNode.path("region").asText();
                        String phone = hospitalNode.path("phone").asText();
                        String province = hospitalNode.path("province").asText();


                        Optional<Region> ifRegionExists = regionRepository.findByName(region);
                        Region newRegion;

                        if (ifRegionExists.isPresent()){
                            newRegion = ifRegionExists.get();
                        } else {
                            newRegion = Region.builder()
                                    .name(region)
                                    .build();
                        }
                        
                        Region createdRegion = regionRepository.save(newRegion);
                        
                        Hospital newHospital = Hospital.builder()
                                .name(name)
                                .gmap(gmapUrl+name)
                                .address(address)
                                .region(createdRegion)
                                .phone(phone)
                                .province(province)
                                .build();
                        
                        hospitalRepository.save(newHospital);

                        System.out.println(jsonNode);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Override
    public Hospital create(HospitalRequestDTO request) {
            Region region = regionService.getOne(request.getRegionId());

        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .gmap(gmapUrl+request.getName())
                .address(request.getAddress())
                .region(region)
                .phone(request.getPhone())
                .province(request.getProvince())
                .build();

        return hospitalRepository.save(hospital);
    }

    @Override
    public Page<HospitalResponseDTO> getAll(
            Pageable pageable,
            String name,
            String province,
            Integer regionId){

            Region region = null;
            if (regionId != null) region = regionRepository.findById(regionId).orElseThrow();

        Specification<Hospital> specHospital = HospitalSpecification.getSpecification(name, province, region);
        Page<Hospital> hospitalPage = hospitalRepository.findAll(specHospital, pageable);

        List<HospitalResponseDTO> hospitalDTOResponses = hospitalPage.stream()
                .map(hospital -> HospitalResponseDTO.builder()
                        .id(hospital.getId())
                        .name(hospital.getName())
                        .address(hospital.getAddress())
                        .region(hospital.getRegion())
                        .phone(hospital.getPhone())
                        .province(hospital.getProvince())
                        .gmap(hospital.getGmap())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(hospitalDTOResponses, pageable, hospitalPage.getTotalElements());
    }

    @Override
    public Hospital getOne(Integer id) {
        return hospitalRepository.findById(id).orElseThrow(()-> new NotFoundException("Hospital with "+id+" isn't found" ));
    }

    @Override
    public Hospital update(HospitalRequestDTO request, Integer id) {
        Region region = regionService.getOne(request.getRegionId());
        Hospital hospital = hospitalRepository.getOne(id);

        hospital.setName(request.getName());
        hospital.setAddress(request.getAddress());
        hospital.setGmap(gmapUrl+request.getName());
        hospital.setProvince(request.getProvince());
        hospital.setRegion(region);
        hospital.setPhone(request.getPhone());

        return hospitalRepository.save(hospital);
    }

    @Override
    public void delete(Integer id) {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(()-> new NotFoundException("Hospital with "+id+" isn't found" ));

        hospitalRepository.delete(hospital);
    }
}
