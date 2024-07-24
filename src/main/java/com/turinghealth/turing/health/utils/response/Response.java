package com.turinghealth.turing.health.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public static <T>ResponseEntity<?> renderJson(T data, String message, HttpStatus status) {
        WebResponse<?> response = WebResponse.builder()
                .message(message)
                .status(status)
                .data(data)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    public static <T> ResponseEntity<?> renderJson(T data, String message) {
        return renderJson(data, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<?> renderJson(T data) {
        return renderJson(data, "Success");
    }


}
