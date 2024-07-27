package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.service.UserService;
import com.turinghealth.turing.health.utils.dto.userDTO.UserRequestDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserUpdateDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.PaginationResponse;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
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
public class UserController {
    private final UserService userService;

    @PostMapping(consumes = "multipart/form-data", path = "/uploadfile")
    @Validated
    public ResponseEntity<?> create(@Valid @ModelAttribute UserRequestDTO request, Errors errors, @RequestPart("file") MultipartFile multipartFile) throws IOException {
        if (errors.hasErrors()) {
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("Create User Failed!", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(userService.create(request, multipartFile), "User Has Been Created!", HttpStatus.OK);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                userService.getOne(id),
                "User Fetched Successfully!",
                HttpStatus.OK
        );
    }

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
