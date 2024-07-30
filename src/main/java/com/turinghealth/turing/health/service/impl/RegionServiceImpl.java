package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.service.RegionService;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionRequestDTO;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionResponseDTO;
import com.turinghealth.turing.health.utils.mapper.RegionMapper;
import com.turinghealth.turing.health.utils.specification.RegionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    
    @Override
    public RegionResponseDTO create(RegionRequestDTO request) {
        Region newRegion = Region.builder()
                .name(request.getName())
                .build();

        Region createdRegion = regionRepository.save(newRegion);
        
        return RegionMapper.regionResponseDTO(createdRegion);
    }

    @Override
    public Page<RegionResponseDTO> getAll(Pageable pageable, String name) {
        Specification<Region> spec = RegionSpecification.getSpecification(name);
        Page<Region> tempRegion = regionRepository.findAll(spec, pageable);
        List<RegionResponseDTO> list = tempRegion.stream()
                .map(RegionMapper::regionResponseDTO)
                .collect(Collectors.toList());
        
        return new PageImpl<>(list, pageable, tempRegion.getNumberOfElements());
    }

    @Override
    public RegionResponseDTO getOne(Integer id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new NotFoundException("Region With ID " + id + " Is Not Found!"));
        
        return RegionMapper.regionResponseDTO(region);
    }

    @Override
    public RegionResponseDTO update(RegionRequestDTO request, Integer id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new NotFoundException("Region With ID " + id + " Is Not Found!"));
        
        region.setName(request.getName());
        
        Region savedRegion = regionRepository.save(region);
        
        return RegionMapper.regionResponseDTO(savedRegion);
    }

    @Override
    public void delete(Integer id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new NotFoundException("Region With ID " + id + " Is Not Found!"));
        
        regionRepository.delete(region);
    }
}
