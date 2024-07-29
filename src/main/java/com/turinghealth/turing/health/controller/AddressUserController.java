package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.service.AddressUserService;
import com.turinghealth.turing.health.utils.dto.addressUserDTO.AddressUserRequestDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressUserController {
    private final AddressUserService addressUserService;


    @PostMapping
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody AddressUserRequestDTO request, Errors errors) {
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while creating address", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                addressUserService.create(request),
                "Address Created Successfully!"
        );
    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        return Response.renderJson(
                addressUserService.getAll(),
                "Address Fetched Successfully!"
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                addressUserService.getOne(id),
                "Address Fetched Successfully!"
        );
    }

    @PutMapping("{id}")
    @Validated
    public ResponseEntity<?> update(@Valid @RequestBody AddressUserRequestDTO request, Errors errors, @PathVariable Integer id) {
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while creating address", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                addressUserService.update(request, id),
                "Address Updated Successfully!"
        );
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        addressUserService.delete(id);

        return Response.renderJson(
                null,
                "Address Deleted Successfully!"
        );
    }

}
