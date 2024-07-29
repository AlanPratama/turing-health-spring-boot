package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.AuthenticationService;
import com.turinghealth.turing.health.utils.dto.authDTO.LoginDTO;
import com.turinghealth.turing.health.utils.dto.authDTO.RegisterDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
})
@Tag(name = "Authentication", description = "Authentication Management APIs")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Validated
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = ErrorsMapper.renderErrors("Register Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return Response.renderJson(
                authenticationService.register(request),
                "Register Successfully!",
                HttpStatus.OK
        );
    }



    @PostMapping("/login")
    @Validated
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = ErrorsMapper.renderErrors("Login Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return Response.renderJson(
                authenticationService.login(request),
                "Login Successfully!",
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}