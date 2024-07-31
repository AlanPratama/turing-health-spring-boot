package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.product.Category;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.CategoryRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.CategoryServiceImpl;
import com.turinghealth.turing.health.service.impl.RegionServiceImpl;
import com.turinghealth.turing.health.utils.dto.CategoryRequestDTO;
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

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserMiddleware userMiddleware;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private User user;

    private Category category;
    private CategoryRequestDTO categoryRequestDTO;

    private Pageable pageable;
    private String name;
    private Integer categoryId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("test@gmail.com");
        user.setRole(Role.ADMIN);

        SecurityContextHolder.setContext(securityContext);

        pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        name = "name";
        categoryId = 1;

        categoryRequestDTO = new CategoryRequestDTO("Test Category2");

        category = new Category();
            category.setId(categoryId);
            category.setCategory("Test Category");
    }

    @Test
    public void getAllCategories_Success() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(categoryPage);
        Page<Category> result = categoryService.getAll(pageable, name);

        assertEquals(categories.size(), result.getTotalElements());
        assertEquals(pageable, result.getPageable());
        verify(categoryRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void createCategory_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        Category expectedResponse = new Category();
            expectedResponse.setId(category.getId());
            expectedResponse.setCategory(category.getCategory());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.create(categoryRequestDTO);

        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getCategory(), result.getCategory());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void getOneCategory_Success() {
        when(categoryRepository.findById(any(Integer.class))).thenReturn(Optional.of(category));
        Category result = categoryService.getOne(categoryId);

        assertEquals(category, result);
        verify(categoryRepository).findById(categoryId);
    }

    @Test
    public void updateCategory_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        CategoryRequestDTO updateRequestDTO = new CategoryRequestDTO("Test Category2");

        Category newCategory = new Category();
            newCategory.setId(categoryId);
            newCategory.setCategory(category.getCategory());

        when(categoryRepository.findById(any(Integer.class))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category result = categoryService.update(updateRequestDTO, categoryId);

        assertEquals(newCategory.getId(), result.getId());
        assertEquals(newCategory.getCategory(), result.getCategory());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void deleteCategory_Success(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category))
                .thenReturn(Optional.empty());
        doNothing().when(categoryRepository).delete(category);

        categoryService.delete(categoryId);

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).delete(category);

        Optional<Category> deletedCategory = categoryRepository.findById(categoryId);
        assertThat(deletedCategory).isEmpty();
    }
}
