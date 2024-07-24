package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.Product;
import com.turinghealth.turing.health.repository.ProductRepository;
import com.turinghealth.turing.health.service.OrderItemService;
import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(ProductDTO request) {
        return productRepository.save(Product.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .description(request.getDescription())
                        .imageLink(request.getImageLink())
                        .categoryId(request.getCategoryId())
                .build());
    }

    @Override
    public Product getOne(Integer id) {
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Integer id, ProductDTO request) {
        Product newProduct = this.getOne(id);
        newProduct.setName(request.getName());
        newProduct.setPrice(request.getPrice());
        newProduct.setDescription(request.getDescription());
        newProduct.setImageLink(request.getImageLink());
        newProduct.setCategoryId(request.getCategoryId());
        return productRepository.save(newProduct);
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
