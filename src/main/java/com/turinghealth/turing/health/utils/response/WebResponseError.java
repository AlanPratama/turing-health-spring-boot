package com.turinghealth.turing.health.utils.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponseError {
    private String message;
    private HttpStatus status;
    private List<String> errors = new ArrayList<>();
}
