package com.turinghealth.turing.health.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.service.HospitalService;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalRequestDTO;
import com.turinghealth.turing.health.utils.dto.hospitalDTO.HospitalResponseDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.PaginationResponse;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/seeding")
    public ResponseEntity<?> hospitalSeeder() throws JsonProcessingException {
        hospitalService.hospitalSeeder();
        return  ResponseEntity.status(HttpStatus.GONE).body("Seeded");
    }

    @PostMapping("/create")
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody HospitalRequestDTO request, Errors errors){
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while creating hospital", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                hospitalService.create(request),
                "Created Hospital",
                HttpStatus.CREATED
        );
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) Integer regionId
            ){
        Page<HospitalResponseDTO> result = hospitalService.getAll(pageable,name,province,regionId);
        PaginationResponse<HospitalResponseDTO> paged = new PaginationResponse<>(result);
        return Response.renderJson(
                paged,
          "Got All Hospitals",
          HttpStatus.FOUND
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Response.renderJson(
                hospitalService.getOne(id),
                "Hospital found",
                HttpStatus.FOUND
        );
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<?> update(@Valid @RequestBody HospitalRequestDTO request, Errors errors, @Valid @PathVariable Integer id){
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem while updating hospital", errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                hospitalService.update(request,id),
                "Hospital Updated",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        hospitalService.delete(id);
        return ResponseEntity.status(HttpStatus.GONE).body("Hospital deleted");
    }

}
