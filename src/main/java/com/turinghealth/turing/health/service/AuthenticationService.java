package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.authDTO.AuthResponseDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.LoginDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthResponseDTO register(RegisterDTO request);
    AuthResponseDTO login(LoginDTO request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
