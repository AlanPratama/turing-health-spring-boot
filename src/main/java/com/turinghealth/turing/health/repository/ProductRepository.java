package com.turinghealth.turing.health.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import com.turinghealth.turing.health.entity.meta.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @EntityGraph(attributePaths = "products")
//    Optional<Product> findByName(String name);
}
