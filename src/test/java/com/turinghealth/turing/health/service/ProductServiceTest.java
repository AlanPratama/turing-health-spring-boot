package com.turinghealth.turing.health.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.product.Category;
import com.turinghealth.turing.health.entity.meta.product.Product;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.CategoryRepository;
import com.turinghealth.turing.health.repository.ProductRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.ProductServiceImpl;
import com.turinghealth.turing.health.utils.dto.productDTO.ProductRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserMiddleware userMiddleware;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private User user;

    private Product product;
    private ProductRequestDTO productRequestDTO;
    private Category category;
    private MockMultipartFile multipartFile;

    private Pageable pageable;
    private String name;
    private Integer productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        name = "name";
        productId = 1;

        user = new User();
            user.setEmail("test@gmail.com");
            user.setRole(Role.ADMIN);

        SecurityContextHolder.setContext(securityContext);

        pageable = PageRequest.of(0,10, Sort.Direction.ASC, "name");

        category = new Category();
            category.setId(1);
            category.setCategory("Test Category");

        multipartFile = new MockMultipartFile("file", "test.png", "image/png", "some-image-content".getBytes());

        productRequestDTO = new ProductRequestDTO();
                productRequestDTO.setName("product2");
                productRequestDTO.setPrice(10);
                productRequestDTO.setDescription("description2");
                productRequestDTO.setCategoryId(category.getId());
                productRequestDTO.setAvailable(true);

        product = Product.builder()
                .id(productId)
                .name("product")
                .category(category)
                .imageLink("http://test-url.com/image.png")
                .price(1)
                .description("description")
                .available(true)
                .build();
    }

    @Test
    void getAllProduct_Success() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);
        Page<Product> result = productService.getAll(pageable, name);

        assertEquals(products.size(), result.getTotalElements());
        assertEquals(pageable, result.getPageable());
        verify(productRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void createProduct_Success() throws IOException {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        when(categoryService.getOne(category.getId())).thenReturn(category);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any(Map.class))).thenReturn(Map.of("url", "http://test-url.com/image.png"));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(productRequestDTO, multipartFile);

        assertEquals(product, result);

        verify(categoryService, times(1)).getOne(category.getId());
        verify(cloudinary.uploader(), times(1)).upload(any(File.class), any(Map.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void getOneProduct_Success() {
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        Product result = productService.getOne(productId);

        assertEquals(product, result);
        verify(productRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    public void updateProduct_Success() throws IOException{
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        ProductRequestDTO updateRequestDTO = new ProductRequestDTO();
        updateRequestDTO.setName("product3");
        updateRequestDTO.setPrice(100);
        updateRequestDTO.setDescription("description3");
        updateRequestDTO.setCategoryId(category.getId());
        updateRequestDTO.setAvailable(true);

        when(categoryService.getOne(category.getId())).thenReturn(category);

        Product newProduct = Product.builder()
                .id(productId)
                .name(updateRequestDTO.getName())
                .category(category)
                .imageLink("http://test-url.com/image.png")
                .price(updateRequestDTO.getPrice())
                .description(updateRequestDTO.getDescription())
                .available(updateRequestDTO.getAvailable())
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(File.class), any(Map.class))).thenReturn(Map.of("url", "http://test-url.com/image.png"));
        when(uploader.destroy(any(String.class), any(Map.class))).thenReturn(Map.of("result", "ok"));

        Product result = productService.update(updateRequestDTO, multipartFile, productId);

        assertEquals(product.getId(), result.getId());
        assertEquals(updateRequestDTO.getName(), result.getName());

        verify(categoryService, times(1)).getOne(category.getId());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(uploader).upload(any(File.class), any(Map.class));
        verify(uploader).destroy(any(String.class), any(Map.class));
    }

    @Test
    public void deleteProduct_Success() throws IOException {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        //test delete cloudinary
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(any(String.class), any(Map.class))).thenReturn(Map.of("result", "ok"));
        productService.delete(productId);

        verify(productRepository).findById(productId);
        verify(productRepository).delete(product);

        //verify cloudinary using public Id from "http://test-url.com/image.png"
        String oldPublicId = "image";
        verify(uploader).destroy(oldPublicId, Map.of());

        Optional<Product> deletedProduct = productRepository.findById(productId);
        assertThat(deletedProduct.isEmpty());
    }
}
