package com.turinghealth.turing.health.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.turinghealth.turing.health.entity.meta.product.Category;
import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.repository.ProductRepository;
import com.turinghealth.turing.health.service.CategoryService;
import com.turinghealth.turing.health.service.OrderItemService;
import com.turinghealth.turing.health.service.ProductService;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductRequestDTO;
import com.turinghealth.turing.health.utils.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final Cloudinary cloudinary;

    @Override
    public Product create(ProductRequestDTO request, MultipartFile multipartFile) throws IOException {
//        Optional<Product> isProduct = productRepository.findByName(request.getName());
//
//        if (isProduct.isPresent()) {
//            throw new NotFoundException("Product Name Already Exist!");
//        }

        Category category = categoryService.getOne(request.getCategoryId());
        String photo = null;

        if (multipartFile != null && !multipartFile.isEmpty()) {
            File convFile = new File( multipartFile.getOriginalFilename() );
            FileOutputStream fos = new FileOutputStream( convFile );
            fos.write( multipartFile.getBytes() );
            fos.close();

            photo = cloudinary.uploader()
                    .upload(convFile, Map.of("public_id", "profile" + request.getName() + "_" + UUID.randomUUID(),
                            "transformation", new Transformation().width(150).height(150).crop("fill").gravity("center")
                    ))
                    .get("url")
                    .toString();

            //delete local photo so only upload via cloudinary
            convFile.delete();
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .available(request.getAvailable() != null ? request.getAvailable() : false)
                .category(category)
                .imageLink(photo)
                .build();

        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAll(Pageable pageable, String name) {
        Specification<Product> spec = ProductSpecification.getSpecification(name);

        return productRepository.findAll(spec, pageable);
    }

    @Override
    public Product getOne(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product With ID " + id + " Is Not Found!"));
    }

    @Override
    public Product update(ProductRequestDTO request, MultipartFile multipartFile, Integer id) throws IOException {
        Product product = this.getOne(id);
        Category category = categoryService.getOne(request.getCategoryId());

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCategory(category);

        if (multipartFile != null && !multipartFile.isEmpty()) {

            if (product.getImageLink() != null) {
                String oldImageLink = product.getImageLink();
                String oldPublicId = oldImageLink.substring(oldImageLink.lastIndexOf("/") + 1, oldImageLink.lastIndexOf("."));

                cloudinary.uploader().destroy(oldPublicId, Map.of());
            }

            File convFile = new File(multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();

            Map uploadResult = cloudinary.uploader().upload(
                    convFile, Map.of("public_id", "profile" + request.getName() + "_" + UUID.randomUUID(),
                            "transformation", new Transformation().width(150).height(150).crop("fill").gravity("center")
                    ));
            String newPhotoLink = uploadResult.get("url").toString();

            product.setImageLink(newPhotoLink);
        }

        return productRepository.save(product);
    }

    @Override
    public void delete(Integer id) throws IOException {
        Product product = this.getOne(id);

        if (product.getImageLink() != null && !product.getImageLink().isEmpty()) {
            String oldImageLink = product.getImageLink();
            String oldPublicId = oldImageLink.substring(oldImageLink.lastIndexOf("/") + 1, oldImageLink.lastIndexOf("."));

            cloudinary.uploader().destroy(oldPublicId, Map.of());
        }

        productRepository.delete(product);
    }
}
