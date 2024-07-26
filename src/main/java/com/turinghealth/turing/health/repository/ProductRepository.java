package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @EntityGraph(attributePaths = "products")
//    Optional<Product> findByName(String name);
}
