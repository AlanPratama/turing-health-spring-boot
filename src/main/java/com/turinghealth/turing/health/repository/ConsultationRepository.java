package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer>, JpaSpecificationExecutor<Consultation> {
}
