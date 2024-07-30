package com.turinghealth.turing.health.utils.mapper;


import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.utils.dto.productDTO.JSONProductDTO;


public class ProductMapper {
    public static Product convertToProduct(JSONProductDTO jsonProductDTO){
        return Product.builder()
                .imageLink(jsonProductDTO.getUrl())
                .name(jsonProductDTO.getBrandName())
                .description(jsonProductDTO.getSlug())
//                .price(jsonProductDTO.getUnitPrice())
                .categoryId(null)
                .build();
    }
}
