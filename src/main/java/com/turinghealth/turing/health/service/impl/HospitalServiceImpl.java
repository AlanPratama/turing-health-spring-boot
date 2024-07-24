package com.turinghealth.turing.health.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.HospitalRepository;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.HospitalService;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalDTO;
import com.turinghealth.turing.health.utils.mapper.HospitalMapper;
import lombok.RequiredArgsConstructor;
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
//        private final RestClient restClient;
        private final RestTemplate restTemplate;

        private String baseUrl = "https://dekontaminasi.com/api/id/covid19/hospitals";
        public static String gmapUrl = "https://www.google.com/maps/place?q=";
    
        @Override
        public List<HospitalDTO> getHospitals() throws JsonProcessingException {
            String response = restTemplate.getForObject(
                    UriComponentsBuilder.fromHttpUrl(baseUrl).toUriString(), String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            HospitalDTO[] hospitalDTOs = objectMapper.readValue(response, HospitalDTO[].class);

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


            return null;
        }
}
