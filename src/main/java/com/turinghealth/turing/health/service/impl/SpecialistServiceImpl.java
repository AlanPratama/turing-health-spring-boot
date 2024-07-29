package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.Specialist;
import com.turinghealth.turing.health.repository.SpecialistRepository;
import com.turinghealth.turing.health.service.SpecialistService;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.SpecialistDTO;
import com.turinghealth.turing.health.utils.specification.SpecialistSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        Specification<Specialist> spec = SpecialistSpecification.getSpecification(name);
        Page<Specialist> specialistPage = specialistRepository.findAll(spec, pageable);

        return specialistPage;
    }

    @Override
    public Specialist getOne(Integer id) {
        return specialistRepository.findById(id).orElseThrow(() -> new NotFoundException("Specialist With ID " + id + " Is Not Found!"));
    }

    @Override
    public Specialist update(SpecialistDTO request, Integer id) {
        Specialist specialist = this.getOne(id);

        specialist.setName(request.getName());

        return specialistRepository.save(specialist);
    }

    @Override
    public void delete(Integer id) {
        Specialist specialist = this.getOne(id);

        specialistRepository.delete(specialist);
    }

}
