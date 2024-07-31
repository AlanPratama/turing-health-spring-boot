package com.turinghealth.turing.health.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.RegionServiceImpl;
import com.turinghealth.turing.health.service.impl.UserServiceImpl;
import com.turinghealth.turing.health.utils.dto.userDTO.UserRequestDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RegionService regionService;

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

    @InjectMocks
    private UserServiceImpl userService;

    private User useradm;

    private User user;
    private Region region;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private UserUpdateDTO userUpdateDTO;
    private MockMultipartFile multipartFile;

    private Pageable pageable;
    private String name;
    private Role role;
    private Integer userId;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        useradm = new User();
        useradm.setEmail("test@gmail.com");
        useradm.setRole(Role.ADMIN);

        SecurityContextHolder.setContext(securityContext);

        name = "name";
        userId = 1;
        role = Role.ADMIN;
        pageable = PageRequest.of(0,10, Sort.Direction.ASC, "name", String.valueOf(role));

        region = new Region();
            region.setId(1);
            region.setName("Test Region");

        multipartFile = new MockMultipartFile("file", "test.png", "image/png", "some-image-content".getBytes());

        userRequestDTO = UserRequestDTO.builder()
                .name("name2")
                .nik("nik2")
                .phone("phone2")
                .address("address2")
                .email("email2")
                .password("password1")
                .regionId(region.getId())
                .role(String.valueOf(Role.ADMIN))
                .build();

        user = User.builder()
                .name("name")
                .nik("nik")
                .phone("phone")
                .address("address")
                .userImageLink("http://test-url.com/image.png")
                .email("email")
                .password("password1")
                .role(Role.ADMIN)
                .region(region)
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .name(user.getName())
                .nik(user.getNik())
                .phone(user.getPhone())
                .address(user.getAddress())
                .imageLink(user.getUserImageLink())
                .email(user.getEmail())
                .role(Role.valueOf(user.getRole().toString()))
                .region(user.getRegion())
                .build();
    }

    @Test
    public void getAllUser_Success(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        List<User> users = Arrays.asList(new User(), new User());
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userPage);
        Page<UserResponseDTO> result = userService.getAll(pageable, name, role);

        assertEquals(users.size(), result.getTotalElements());
        assertEquals(pageable, result.getPageable());
        verify(userRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

//    @Test
//    public void createUser_Success() throws IOException {
//        when(regionRepository.findById(region.getId())).thenReturn(Optional.of(region));
//        when(passwordEncoder.encode(anyString())).thenReturn("password1");
//
//        when(cloudinary.uploader()).thenReturn(uploader);
//        when(uploader.upload(any(), any(Map.class))).thenReturn(Map.of("url", "http://test-url.com/image.png"));
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        userResponseDTO = userService.create(userRequestDTO, multipartFile);
//
//        assertEquals(user.getName(), userResponseDTO.getName());
//        assertEquals(user.getPassword(), userRequestDTO.getPassword());
//        assertEquals(user.getUserImageLink(), userResponseDTO.getImageLink());
//
//        verify(regionRepository, times(1)).findById(any(Integer.class));
//        verify(passwordEncoder, times(1)).encode(anyString());
//        verify(cloudinary.uploader(), times(1)).upload(any(File.class), any(Map.class));
//        verify(userRepository, times(1)).save(any(User.class));
//    }

    @Test
    public void getOneUser_Success(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.getOne(userId);

        assertEquals(userResponseDTO.getId(), result.getId());
        assertEquals(userResponseDTO.getName(), result.getName());
        assertEquals(userResponseDTO.getNik(), result.getNik());
        assertEquals(userResponseDTO.getPhone(), result.getPhone());
        assertEquals(userResponseDTO.getAddress(), result.getAddress());
        assertEquals(userResponseDTO.getImageLink(), result.getImageLink());
        assertEquals(userResponseDTO.getEmail(), result.getEmail());
        assertEquals(userResponseDTO.getRole(), result.getRole());
        assertEquals(userResponseDTO.getRegion(), result.getRegion());

        verify(userRepository).findById(userId);
    }

    @Test
    public void updateUser_Success() throws IOException {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        UserUpdateDTO updateUserDTO = UserUpdateDTO.builder()
                .name("name3")
                .nik("nik3")
                .phone("phone3")
                .address("address3")
                .email("email3")
                .regionId(region.getId())
                .role(role)
                .build();

        when(regionRepository.findById(updateUserDTO.getRegionId())).thenReturn(Optional.of(region));

        User newUser = new User();
        newUser.setId(userId);
        newUser.setName(updateUserDTO.getName());
        newUser.setNik(updateUserDTO.getNik());
        newUser.setPhone(updateUserDTO.getPhone());
        newUser.setAddress(updateUserDTO.getAddress());
        newUser.setEmail(updateUserDTO.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setRegion(region);
        newUser.setRole(Role.valueOf(role.toString()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(File.class), any(Map.class))).thenReturn(Map.of("url", "http://test-url.com/image.png"));
        when(uploader.destroy(any(String.class), any(Map.class))).thenReturn(Map.of("result", "ok"));

        UserResponseDTO result = userService.update(updateUserDTO, userId, multipartFile);

        assertEquals(newUser.getName(), result.getName());
        assertEquals(newUser.getNik(), result.getNik());
        assertEquals(newUser.getPhone(), result.getPhone());
        assertEquals(newUser.getAddress(), result.getAddress());
        assertEquals(newUser.getEmail(), result.getEmail());
        assertEquals(newUser.getRole(), result.getRole());
        assertEquals(newUser.getRegion().getId(), result.getRegion().getId());

        verify(regionRepository).findById(updateUserDTO.getRegionId());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
        verify(uploader).upload(any(File.class), any(Map.class));
        verify(uploader).destroy(anyString(), any(Map.class));
    }

    @Test
    public void deleteUser_Success() throws IOException{
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty());
        doNothing().when(userRepository).delete(user);

        //test delete cloudinary
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(any(String.class), any(Map.class))).thenReturn(Map.of("result", "ok"));
        userService.delete(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);

        //verify cloudinary using public Id from "http://test-url.com/image.png"
        String oldPublicId = "image";
        verify(uploader).destroy(oldPublicId, Map.of());

        Optional<User> deletedUser = userRepository.findById(userId);
        assertThat(deletedUser.isEmpty());
    }
}
