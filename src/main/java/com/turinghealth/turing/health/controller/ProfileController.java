package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.ProfileService;
import com.turinghealth.turing.health.utils.dto.profileDTO.ChangePasswordDTO;
import com.turinghealth.turing.health.utils.dto.profileDTO.UserProfileDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponse;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Tag(name = "Profile", description = "Profile Management APIs")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Get Info Profile", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get Info Profile Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @GetMapping
    public ResponseEntity<?> getInfoProfile() {
        return Response.renderJson(
                profileService.getInfoProfile(),
                "Get Info Profile Successfully!",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update Info Profile", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile Updated Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @PutMapping(consumes = "multipart/form-data")
    @Validated
    public ResponseEntity<?> updateInfoProfile(
            @Valid @ModelAttribute UserProfileDTO request,
            Errors errors,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = ErrorsMapper.renderErrors("Update Profile Failed!", errors);
            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return Response.renderJson(
                profileService.updateInfoProfile(request, multipartFile),
                "Profile Updated Successfully!",
                HttpStatus.OK
        );
    }


    @Operation(summary = "Change Password", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password Changed Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO request, Errors errors) {
        if (errors.hasErrors()) {
            WebResponseError<?> mapErrors = ErrorsMapper.renderErrors("Update Profile Failed!", errors);

            return ResponseEntity.status(mapErrors.getStatus()).body(mapErrors);
        }

        return Response.renderJson(
                profileService.changePassword(request),
                "Password Changed Successfuly!",
                HttpStatus.OK
        );
    }

}
