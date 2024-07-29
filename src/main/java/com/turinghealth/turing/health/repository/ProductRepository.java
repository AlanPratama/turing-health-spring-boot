package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
