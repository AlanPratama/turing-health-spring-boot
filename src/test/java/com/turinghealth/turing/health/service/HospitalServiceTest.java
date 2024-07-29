package com.turinghealth.turing.health.service;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.HospitalRepository;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.impl.HospitalServiceImpl;
import com.turinghealth.turing.health.service.impl.RegionServiceImpl;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalRequestDTO;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.xml.transform.Result;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private RegionService regionService;

    @Mock
    private HospitalService hospitalServiceInterface;

    @InjectMocks
    private HospitalServiceImpl hospitalService;

    private Hospital hospital;
    private Region region;
    private HospitalRequestDTO hospitalRequestDTO;
    private HospitalResponseDTO hospitalResponseDTO;

    private Pageable pageable;
    private String name;
    private String province;
    private Integer hospitalId;
    private String gmapUrl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        pageable = PageRequest.of(0,10, Sort.Direction.ASC, "name", "province", "region");
        name = "region";
        province = "province";
        hospitalId = 1;
        gmapUrl = "gmaplink/";

        region = new Region();
            region.setId(1);
            region.setName("Test Region");

        hospitalRequestDTO = new HospitalRequestDTO();
                hospitalRequestDTO.setName("Test Hospital2");
                hospitalRequestDTO.setAddress("address2");
                hospitalRequestDTO.setRegionId(region.getId());
                hospitalRequestDTO.setPhone("phone2");
                hospitalRequestDTO.setProvince("province2");

        hospital = new Hospital();
            hospital.setId(hospitalId);
            hospital.setName("Test Hospital");
            hospital.setAddress("address");
            hospital.setGmap(gmapUrl+"Test Hospital");
            hospital.setPhone("phone");
            hospital.setProvince("province");
            hospital.setRegion(region);
    }

    @Test
    public void getAllHospitals_Success(){
        List<Hospital> hospitals = Arrays.asList(new Hospital(), new Hospital());
        Page<Hospital> hospitalPage = new PageImpl<>(hospitals, pageable, hospitals.size());

        when(regionRepository.findById(region.getId())).thenReturn(Optional.of(region));
        when(hospitalRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(hospitalPage);
        Page<HospitalResponseDTO> result = hospitalService.getAll(pageable, name, province, region.getId());

        assertEquals(hospitals.size(), result.getTotalElements());
        assertEquals(pageable, result.getPageable());
        assertEquals(hospitals.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(hospitals.get(1).getName(), result.getContent().get(1).getName());
        verify(regionRepository, times(1)).findById(region.getId());
        verify(hospitalRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void createHospital_Success(){
        when(regionRepository.findById(hospitalRequestDTO.getRegionId())).thenReturn(Optional.of(region));
        when(regionService.getOne(hospitalRequestDTO.getRegionId())).thenReturn(region);
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(hospital);

        Hospital result = hospitalService.create(hospitalRequestDTO);

        assertEquals(hospital.getName(), result.getName());
        assertEquals(hospital.getAddress(), result.getAddress());
        assertEquals(hospital.getGmap(), result.getGmap());
        assertEquals(hospital.getPhone(), result.getPhone());
        assertEquals(hospital.getProvince(), result.getProvince());
        assertEquals(hospital.getRegion(), result.getRegion());

        verify(regionService, times(1)).getOne(hospitalRequestDTO.getRegionId());
        verify(hospitalRepository, times(1)).save(any(Hospital.class));
    }

    @Test
    public void getOneHospital_Success(){
        when(hospitalRepository.findById(any(Integer.class))).thenReturn(Optional.of(hospital));
        Hospital result = hospitalService.getOne(hospitalId);

        assertEquals(hospital,result);
        verify(hospitalRepository).findById(hospitalId);
    }

    @Test
    public void updateHospital_Success(){
        HospitalRequestDTO updateHospitalDTO = new HospitalRequestDTO();
        hospitalRequestDTO.setName("Test Hospital3");
        hospitalRequestDTO.setAddress("address3");
        hospitalRequestDTO.setRegionId(region.getId());
        hospitalRequestDTO.setPhone("phone3");
        hospitalRequestDTO.setProvince("province3");

        Hospital newHospital = new Hospital();
            newHospital.setId(hospitalId);
            newHospital.setName(updateHospitalDTO.getName());
            newHospital.setAddress(updateHospitalDTO.getAddress());
            newHospital.setGmap(gmapUrl+updateHospitalDTO);
            newHospital.setPhone(updateHospitalDTO.getPhone());
            newHospital.setProvince(updateHospitalDTO.getProvince());
            newHospital.setRegion(region);

        when(hospitalRepository.getOne(hospitalId)).thenReturn(hospital);
        when(regionService.getOne(updateHospitalDTO.getRegionId())).thenReturn(region);
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(newHospital);


        Hospital result = hospitalService.update(updateHospitalDTO, hospitalId);

        assertEquals(newHospital.getName(), result.getName());
        assertEquals(newHospital.getAddress(), result.getAddress());
        assertEquals(newHospital.getGmap(), result.getGmap());
        assertEquals(newHospital.getPhone(), result.getPhone());
        assertEquals(newHospital.getProvince(), result.getProvince());
        assertEquals(newHospital.getRegion(), result.getRegion());


        verify(hospitalRepository, times(1)).getOne(hospitalId);
        verify(hospitalRepository, times(1)).save(any(Hospital.class));
    }

    @Test
    public void deleteHospital_Success(){
        when(hospitalRepository.findById(hospitalId))
                .thenReturn(Optional.of(hospital))
                .thenReturn(Optional.empty());
        doNothing().when(hospitalRepository).delete(hospital);

        hospitalService.delete(hospitalId);

        verify(hospitalRepository).findById(hospitalId);
        verify(hospitalRepository).delete(hospital);

        Optional<Hospital> deletedHospital = hospitalRepository.findById(hospitalId);
        assertThat(deletedHospital).isEmpty();
    }

}
