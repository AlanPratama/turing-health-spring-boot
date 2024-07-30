package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductDTO;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product create(@RequestBody ProductDTO request){
        return productService.create(request);
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable Integer id){
        return productService.getOne(id);
    }

    @GetMapping
    public ProductResponseDTO productSeeder(){
        return productService.productSeeder();
    }


    @DeleteMapping
    public void deleteById(Integer id){
        productService.delete(id);
    }


}
