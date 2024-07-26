package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.AuthenticationService;
import com.turinghealth.turing.health.utils.dto.authDTO.AuthResponseDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.LoginDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO register(RegisterDTO request) {
        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User createdUser = userRepository.save(user);

        // UNCOMPLETED

        return null;
    }

    @Override
    public AuthResponseDTO login(LoginDTO request) {


        return null;
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
