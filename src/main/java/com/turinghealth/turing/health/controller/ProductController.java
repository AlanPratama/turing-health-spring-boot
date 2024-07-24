package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.meta.Product;
import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductDTO;
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
    public List<Product> getAll(){
        return productService.getAll();
    }

    @DeleteMapping
    public void deleteById(Integer id){
        productService.delete(id);
    }

}
