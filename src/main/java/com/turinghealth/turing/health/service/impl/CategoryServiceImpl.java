package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.product.Category;
import com.turinghealth.turing.health.repository.CategoryRepository;
import com.turinghealth.turing.health.service.CategoryService;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.CategoryRequestDTO;
import com.turinghealth.turing.health.utils.specification.CategorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(CategoryRequestDTO request) {
        Category category = Category.builder()
                .category(request.getCategory())
                .build();

        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> getAll(Pageable pageable, String category) {
        Specification<Category> spec = CategorySpecification.getSpecification(category);

        return categoryRepository.findAll(spec, pageable);
    }

    @Override
    public Category getOne(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category With ID " + id + " Is Not Found!"));
    }

    @Override
    public Category update(CategoryRequestDTO request, Integer id) {
        Category category = this.getOne(id);
        category.setCategory(request.getCategory());

        return categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id) {
        Category category = this.getOne(id);

        categoryRepository.delete(category);
    }
}
