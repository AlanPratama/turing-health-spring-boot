package com.turinghealth.turing.health.controller;

import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.service.RegionService;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionRequestDTO;
import com.turinghealth.turing.health.utils.dto.regionDTO.RegionResponseDTO;
import com.turinghealth.turing.health.utils.mapper.ErrorsMapper;
import com.turinghealth.turing.health.utils.response.PaginationResponse;
import com.turinghealth.turing.health.utils.response.Response;
import com.turinghealth.turing.health.utils.response.WebResponseError;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/create")
    @Validated
    public ResponseEntity<?> create(@Valid @RequestBody RegionRequestDTO request, Errors errors){
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem when creating region",errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                regionService.create(request),
                "Region has been created",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false)String name
            ){
        Page<RegionResponseDTO> result = regionService.getAll(pageable,name);
        PaginationResponse<RegionResponseDTO> paged = new PaginationResponse<>(result);
        return  Response.renderJson(
                paged,
                "Got All",
                HttpStatus.FOUND
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Response.renderJson(
                regionService.getOne(id),
                "Region found",
                HttpStatus.FOUND
        );
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<?> update(@Valid @RequestBody RegionRequestDTO request,Errors errors, @PathVariable Integer id){
        if (errors.hasErrors()){
            WebResponseError<?> responseError = ErrorsMapper.renderErrors("There's a problem when updating region",errors);
            return ResponseEntity.status(responseError.getStatus()).body(responseError);
        }

        return Response.renderJson(
                regionService.update(request,id),
                "Region updated",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        regionService.delete(id);
        return ResponseEntity.status(HttpStatus.GONE).body("Region deleted");
    }
}
