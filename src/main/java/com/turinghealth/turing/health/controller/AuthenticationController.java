package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.AuthenticationService;
import com.turinghealth.turing.health.utils.dto.authDTO.RegisterDTO;
import com.turinghealth.turing.health.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO request) {
        return Response.renderJson(
                authenticationService.register(request),
                "Register Successfully!",
                HttpStatus.OK
        );
    }



    // UNCOMPLETED!

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
//        return Response.renderJson(
//                null,
//                "Login Successfully!",
//                HttpStatus.OK
//        );
//    }

}