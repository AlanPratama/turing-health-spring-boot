package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface SpecialistRepository extends JpaRepository<Specialist, Integer>, JpaSpecificationExecutor<Specialist> {
}
