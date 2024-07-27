package com.turinghealth.turing.health.service;


import com.turinghealth.turing.health.entity.meta.Product;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductDTO;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    Product create(ProductDTO request);
    Product getOne(Integer id);
    List<Product> getAll();
    Product update(Integer id, ProductDTO request);
    void delete(Integer id);
    ProductResponseDTO productSeeder();
}
