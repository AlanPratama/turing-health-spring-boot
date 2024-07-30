package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    Product create(ProductRequestDTO request, MultipartFile multipartFile) throws IOException;
    Page<Product> getAll(Pageable pageable, String name);
    Product getOne(Integer id);
    Product update(ProductRequestDTO request, MultipartFile multipartFile, Integer id) throws IOException;
    void delete(Integer id) throws IOException;

}
