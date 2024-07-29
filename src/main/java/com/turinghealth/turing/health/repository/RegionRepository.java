package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>, JpaSpecificationExecutor<Region> {
    Optional<Region> findByName(String name);
}
