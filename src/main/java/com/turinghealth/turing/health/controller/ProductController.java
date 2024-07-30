package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductRequestDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.PaginationResponse;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    @Validated
    public ResponseEntity<?> create(
            @Valid @ModelAttribute ProductRequestDTO request,
            Errors errors,
            @RequestPart("file")MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Create Product Failed!", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                productService.create(request, multipartFile),
                "Product Created Successfully!"
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        return Response.renderJson(
                new PaginationResponse<>(productService.getAll(pageable, name)),
                "Product Fetched Successfully!"
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                productService.getOne(id),
                "Product Fetched Successfully!"
        );
    }


    @PutMapping(consumes = "multipart/form-data")
    @Validated
    public ResponseEntity<?> update(
            @Valid @ModelAttribute ProductRequestDTO request,
            Errors errors,
            @RequestPart("file")MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Update Product Failed!", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                productService.create(request, multipartFile),
                "Product Updated Successfully!"
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws IOException {
        productService.delete(id);

        return Response.renderJson(
                null,
                "Product Deleted Successfully!"
        );
    }

}
