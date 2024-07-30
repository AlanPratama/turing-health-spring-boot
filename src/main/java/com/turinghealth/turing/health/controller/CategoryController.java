package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.product.Category;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.CategoryService;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.ValidateException;
import com.turinghealth.turing.health.utils.dto.CategoryRequestDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.PaginationResponse;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @PostMapping
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequestDTO request, Errors errors) {


        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while creating hospital", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                categoryService.create(request),
                "Category Created Successfully!"
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 15)Pageable pageable,
            @RequestParam(required = false) String category
    ) {
        Page<Category> result = categoryService.getAll(pageable, category);
        PaginationResponse<Category> response = new PaginationResponse<>(result);

        return Response.renderJson(
                response,
                "Category Fetched Successfully!"
        );
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                categoryService.getOne(id),
                "Category Fetched Successfully!"
        );
    }


    @PutMapping("{id}")
    @Validated
    public ResponseEntity<?> update(@Valid @RequestBody CategoryRequestDTO request, Errors errors, @PathVariable Integer id) {
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while creating hospital", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                categoryService.update(request, id),
                "Category Updated Successfully!"
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        categoryService.delete(id);

        return Response.renderJson(
                null,
                "Category Deleted Successfully!"
        );
    }

}
