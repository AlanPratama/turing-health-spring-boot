package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.Specialist;
import com.turinghealth.turing.health.repository.SpecialistRepository;
import com.turinghealth.turing.health.service.SpecialistService;
import com.turinghealth.turing.health.utils.dto.SpecialistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;

    @Override
    public Specialist create(SpecialistDTO request) {
        Specialist specialist = Specialist.builder()
                .name(request.getName())
                .build();

        return specialistRepository.save(specialist);
    }

    @Override
    public Page<Specialist> getAll(Pageable pageable, String name) {
        return null;
    }

    @Override
    public Specialist getOne(Integer id) {
        return null;
    }

    @Override
    public Specialist update(SpecialistDTO request, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
