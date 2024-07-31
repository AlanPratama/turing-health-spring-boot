package com.turinghealth.turing.health.service;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.transaction.AddressUser;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.AddressUserRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.AddressUserServiceImpl;
import com.turinghealth.turing.health.utils.dto.addressUserDTO.AddressUserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressUserServiceTest {
    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserMiddleware userMiddleware;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressUserRepository addressUserRepository;

    @InjectMocks
    private AddressUserServiceImpl addressUserService;

    private User userMember;
    private User userDoctor;

    private AddressUser addressUser;
    private AddressUserRequestDTO addressUserRequestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userMember = new User();
            userMember.setEmail("member@gmail.com");
            userMember.setRole(Role.MEMBER);

        userDoctor = User.builder()
                .email("doctor@gmail.com")
                .role(Role.DOCTOR).build();

        SecurityContextHolder.setContext(securityContext);

        addressUser = AddressUser.builder()
                .id(1)
                .user(userMember)
                .buyerName("Buyer")
                .buyerPhone("phone")
                .city("city")
                .posCode("poscode")
                .addressDetail("addressDetail")
                .fixPoint("fixPoint")
                .type("type")
                .message("message")
                .build();

        addressUserRequestDTO = AddressUserRequestDTO.builder()
                .buyerName("Buyer")
                .buyerPhone("phone")
                .city("city")
                .posCode("poscode")
                .addressDetail("addressDetail")
                .fixPoint("fixPoint")
                .type("type")
                .message("message")
                .build();

    }

//    @Test
//    void getAllAddressUserMember_for_Member_Success() {
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.getName()).thenReturn(userMember.getEmail());
//
//        when(userRepository.findByEmail(userMember.getEmail())).thenReturn(Optional.of(userMember));
//        when(addressUserRepository.findAll(any(Specification.class))).thenReturn(List.of(addressUser));
//
//        List<AddressUser> addressUsers = addressUserService.getAll();
//        assertNotNull(addressUsers);
//        assertEquals(1, addressUsers.size());
//        verify(addressUserRepository, times(1)).findAll(any(Specification.class));
//    }

//    @Test
//    void getAllAddressUser_for_Doctor_Success() {
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.getName()).thenReturn("doctore@gmail.com");
//        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(userDoctor));
//
//        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userDoctor));
//        when(addressUserRepository.findAll(any(Specification.class))).thenReturn(List.of(addressUser));
//
//        List<AddressUser> addressUsers = addressUserService.getAll();
//
//        assertNotNull(addressUsers);
//        assertEquals(1, addressUsers.size());
//        verify(addressUserRepository, times(1)).findAll(any(Specification.class));
//    }
}
