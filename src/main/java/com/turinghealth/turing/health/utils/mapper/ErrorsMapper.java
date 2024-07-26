package com.turinghealth.turing.health.utils.mapper;

import com.turinghealth.turing.health.utils.response.WebResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ErrorsMapper {
    public static WebResponseError<?> renderErrors(String message, Errors errors) {
        WebResponseError<?> responseError = new WebResponseError<>();

        for (ObjectError error : errors.getAllErrors()) {
            responseError.getErrors().add(error.getDefaultMessage());
        }

        responseError.setStatus(HttpStatus.BAD_REQUEST);
        responseError.setMessage(message);

        return responseError;
    }
}
