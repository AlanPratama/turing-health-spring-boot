package com.turinghealth.turing.health.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.HospitalRepository;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.HospitalService;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalDTOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
        private final HospitalRepository hospitalRepository;
        private final RegionRepository regionRepository;
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
                        
                        Region newRegion = Region.builder()
                                .name(region)
                                .build();
                        
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
    public Hospital create(Hospital request) {
        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .gmap(gmapUrl+request.getName())
                .address(request.getAddress())
                .region(request.getRegion())
                .phone(request.getPhone())
                .province(request.getProvince())
                .build();

        return hospitalRepository.save(hospital);
    }

    @Override
    public Page<HospitalDTOResponse> getAll(Pageable pageable, String name, String address, String Region) {
//             Specification<Hospital> spec =
        return null;
        }

    @Override
    public Hospital getOne(Integer id) {
        return null;
    }

    @Override
    public Hospital update(Hospital request, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
