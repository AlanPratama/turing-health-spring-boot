package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository

public interface HospitalRepository extends JpaRepository<Hospital, Integer>, JpaSpecificationExecutor<Hospital> {
}
