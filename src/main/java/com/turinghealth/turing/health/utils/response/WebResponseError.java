package com.turinghealth.turing.health.utils.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponseError<T> {
    private String message;
    private HttpStatus status;
    private List<String> errors = new ArrayList<>();
}
