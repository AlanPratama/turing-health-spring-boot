package com.turinghealth.turing.health.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.ProfileServiceImpl;
import com.turinghealth.turing.health.utils.dto.profileDTO.ChangePasswordDTO;
import com.turinghealth.turing.health.utils.dto.profileDTO.UserProfileDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
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
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMiddleware middleware;

    @Mock
    private Authentication authentication;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserResponseDTO userResponseDTO;
    private ChangePasswordDTO changePasswordDTO;
    private Region region;
    private UserProfileDTO userProfileDTO;
    private MockMultipartFile multipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        region = new Region();
            region.setId(1);
            region.setName("Test Region");

        user = User.builder()
                .id(1)
                .name("name")
                .nik("nik")
                .phone("phone")
                .address("address")
                .userImageLink("http://test-url.com/image.png")
                .email("test@gmail.com")
                .password("encodedPassword")
                .role(Role.MEMBER)
                .region(region)
                .build();

        userProfileDTO = new UserProfileDTO();
            userProfileDTO.setName("name");
            userProfileDTO.setNik("nik");
            userProfileDTO.setPhone("phone");
            userProfileDTO.setAddress("address");
            userProfileDTO.setEmail("test@gmail.com");
            userProfileDTO.setRegion(region);

        changePasswordDTO = new ChangePasswordDTO();
            changePasswordDTO.setOldPassword("encodedPassword");
            changePasswordDTO.setNewPassword("newPassword");
            changePasswordDTO.setConfirmPassword("newPassword");

        multipartFile = new MockMultipartFile("file", "test.png", "image/png", "some-image-content".getBytes());

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getInfoProfile_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        UserResponseDTO result = profileService.getInfoProfile();

        assertEquals(user.getName(), result.getName());
        verify(userRepository,times(1)).findByEmail(user.getEmail());
    }

    @Test
    void updateProfile_Success() throws IOException {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any(Map.class))).thenReturn(Map.of("url", "http://test-url.com/image.png"));
        when(uploader.destroy(any(String.class), any(Map.class))).thenReturn(Map.of("result", "ok"));

        UserResponseDTO result = profileService.updateInfoProfile(userProfileDTO, multipartFile);

        assertEquals(user.getName(), result.getName());
        verify(userRepository,times(1)).findByEmail(result.getEmail());
    }

    @Test
    void changePassword_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("encodedPassword", user.getPassword())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = profileService.changePassword(changePasswordDTO);

        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode("newPassword");

    }
}
