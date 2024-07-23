package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
