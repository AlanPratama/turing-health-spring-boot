package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
}
