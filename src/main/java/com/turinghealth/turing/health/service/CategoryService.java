package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.meta.product.Category;
import com.turinghealth.turing.health.utils.dto.CategoryRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category create(CategoryRequestDTO request);
    Page<Category> getAll(Pageable pageable, String category);
    Category getOne(Integer id);
    Category update(CategoryRequestDTO request, Integer id);
    void delete(Integer id);

}
