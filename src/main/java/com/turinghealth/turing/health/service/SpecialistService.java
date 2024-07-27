package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.meta.Specialist;
import com.turinghealth.turing.health.utils.dto.SpecialistDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecialistService {

    Specialist create(SpecialistDTO request);
    Page<Specialist> getAll(Pageable pageable, String name);
    Specialist getOne(Integer id);
    Specialist update(SpecialistDTO request, Integer id);
    void delete(Integer id);

}
