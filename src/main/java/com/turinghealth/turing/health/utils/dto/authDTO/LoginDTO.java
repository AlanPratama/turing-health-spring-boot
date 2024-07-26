package com.turinghealth.turing.health.utils.dto.authDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email Cannot Be Blank!")
    private String email;

    @NotBlank(message = "Password Cannot Be Blank!")
    private String password;
}
