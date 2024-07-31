package com.turinghealth.turing.health.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turinghealth.turing.health.configuration.JwtService;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.entity.meta.Token;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.repository.TokenRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.AuthenticationServiceImpl;
import com.turinghealth.turing.health.utils.dto.authDTO.AuthResponseDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.LoginDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;
    private Region region;
    private AuthResponseDTO authResponseDTO;
    private RegisterDTO registerDTO;
    private LoginDTO loginDTO;
    private String jwtToken;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        jwtToken = "token";

        region = Region.builder()
                .id(1)
                .name("Test Region")
                .build();

        user = User.builder()
                .name("name")
                .nik("nik")
                .phone("phone")
                .address("address")
                .userImageLink("http://test-url.com/image.png")
                .email("email")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .region(region)
                .build();

        authResponseDTO = AuthResponseDTO.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        registerDTO = RegisterDTO.builder()
                .name("name")
                .phone("phone")
                .email("email")
                .password("encodedPassword")
                .build();

        loginDTO = LoginDTO.builder()
                .email("email")
                .password("encodedPassword")
                .build();
    }

    @Test
    public void registerUser_Success() {
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(authResponseDTO.getAccessToken());
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(authResponseDTO.getRefreshToken());

        AuthResponseDTO response = authenticationService.register(registerDTO);

        assertEquals(authResponseDTO.getAccessToken(), response.getAccessToken());
        assertEquals(authResponseDTO.getRefreshToken(), response.getRefreshToken());

        verify(passwordEncoder, times(1)).encode(registerDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(jwtService, times(1)).generateRefreshToken(any(User.class));
    }

    @Test
    public void loginUser_Success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn(authResponseDTO.getAccessToken());
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(authResponseDTO.getRefreshToken());

        AuthResponseDTO response = authenticationService.login(loginDTO);

        assertEquals(authResponseDTO.getAccessToken(), response.getAccessToken());
        assertEquals(authResponseDTO.getRefreshToken(), response.getRefreshToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginDTO.getEmail());
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(jwtService, times(1)).generateRefreshToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

//    @Test
//    public void refreshToken_Success() throws IOException {
//        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer "+authResponseDTO.getRefreshToken());
//        when(jwtService.extractUsername(any(String.class))).thenReturn(user.getEmail());
//        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
//        when(jwtService.isTokenValid(any(String.class), any(User.class))).thenReturn(true);
//        when(jwtService.generateToken(any(User.class))).thenReturn("newToken");
//
//        authenticationService.refreshToken(httpServletRequest, httpServletResponse);
//
//        verify(jwtService, times(1)).generateToken(any(User.class));
//        verify(objectMapper, times(1)).writeValue(eq(httpServletResponse.getOutputStream()), any(AuthResponseDTO.class));
//    }

}
