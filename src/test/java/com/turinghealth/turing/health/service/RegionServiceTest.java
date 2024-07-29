package com.turinghealth.turing.health.service;


import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.impl.RegionServiceImpl;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionRequestDTO;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.xml.transform.Result;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionServiceImpl regionService;

    private Region region;
    private RegionRequestDTO regionRequestDTO;
    private RegionResponseDTO regionResponseDTO;

    private Pageable pageable;
    private String name;
    private Integer regionId;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        name = "name";
        regionId = 1;

        regionRequestDTO = new RegionRequestDTO("Test Region");

        region = new Region();
            region.setId(regionId);
            region.setName("Test Region");
    }

    @Test
    public void getAllRegion_Success(){
        List<Region> regions = Arrays.asList(new Region(), new Region());
        Page<Region> regionPage = new PageImpl<>(regions, pageable, regions.size());

        when(regionRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(regionPage);
        Page<RegionResponseDTO> result = regionService.getAll(pageable, name);

        assertEquals(regions.size(), result.getTotalElements());
        assertEquals(pageable, result.getPageable());
        verify(regionRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void createRegion_Success(){
        region = new Region();
        region.setName("Test Region");
        RegionResponseDTO expectedResponse = new RegionResponseDTO(region.getId(), region.getName());

        when(regionRepository.save(any(Region.class))).thenReturn(region);

        RegionResponseDTO result = regionService.create(regionRequestDTO);

        assertEquals(expectedResponse.getName(), result.getName());
        assertEquals(expectedResponse.getId(), result.getId());
        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    public void getOneRegion_Success(){
        when(regionRepository.findById(any(Integer.class))).thenReturn(Optional.of(region));
        Region result = regionService.getOne(regionId);

        assertEquals(region,result);
        verify(regionRepository).findById(regionId);
    }

    @Test
    public void updateRegion_Success(){
        RegionRequestDTO updateRequestDTO = new RegionRequestDTO("Another test Region");

        Region newRegion = Region.builder()
                .id(regionId)
                .name(updateRequestDTO.getName())
                .build();

        when(regionRepository.findById(regionId)).thenReturn(Optional.of(region));
        when(regionRepository.save(any(Region.class))).thenReturn(newRegion);

        RegionResponseDTO responseDTO = regionService.update(updateRequestDTO, regionId);

        assertEquals(region.getId(), responseDTO.getId());
        assertEquals(updateRequestDTO.getName(), responseDTO.getName());
        verify(regionRepository, times(1)).findById(regionId);
        verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    public void deleteRegion_Success(){
        when(regionRepository.findById(regionId))
                .thenReturn(Optional.of(region))
                        .thenReturn(Optional.empty());
        doNothing().when(regionRepository).delete(region);

        regionService.delete(regionId);

        verify(regionRepository).findById(regionId);
        verify(regionRepository).delete(region);

        Optional<Region> deletedRegion = regionRepository.findById(regionId);
        assertThat(deletedRegion).isEmpty();
    }
}
