package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.service.UserService;
import com.turinghealth.turing.health.utils.dto.userDTO.UserRequestDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserUpdateDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.PaginationResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Users Management APIs")
public class UserController {
    private final UserService userService;

//    @Operation(summary = "Create New User", security = @SecurityRequirement(name = "bearerAuth"))
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "User Created Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
//            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
//    })
//    @PostMapping(consumes = "multipart/form-data", path = "/uploadfile")
//    @Validated
//    public ResponseEntity<?> create(@Valid @ModelAttribute UserRequestDTO request, Errors errors, @RequestPart("file") MultipartFile multipartFile) throws IOException {
//        if (errors.hasErrors()) {
//            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Create User Failed!", errors);
//            return ResponseEntity.status(responseError.getStatus()).body(responseError);
//        }
//
//        return Response.renderJson(userService.create(request, multipartFile), "User Has Been Created!", HttpStatus.OK);
//    }


    @Operation(summary = "Get All Users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get All Users Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Role role
    ) {
        return Response.renderJson(
                new PaginationResponse<>(userService.getAll(pageable, name, role)),
                "Users Fetched Successfully!",
                HttpStatus.OK
        );
    }


    @Operation(summary = "Get One User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get One User Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                userService.getOne(id),
                "User Fetched Successfully!",
                HttpStatus.OK
        );
    }


    @Operation(summary = "Update User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Updated Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @PutMapping(value = "{id}", consumes = "multipart/form-data")
    @Validated
    public ResponseEntity<?> update(
            @Valid @ModelAttribute UserUpdateDTO request,
            Errors errors,
            @PathVariable Integer id,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Update User Failed!", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                userService.update(request, id, multipartFile),
                "User Has Been Update",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete User", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Deleted Successfully!", content = { @Content(schema = @Schema(implementation = WebResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("{id}")

    public ResponseEntity<?> delete(@PathVariable Integer id) throws IOException{
        userService.delete(id);

        return Response.renderJson(
                null,
                "User Has Been Deleted!",
                HttpStatus.OK
        );
    }


}
