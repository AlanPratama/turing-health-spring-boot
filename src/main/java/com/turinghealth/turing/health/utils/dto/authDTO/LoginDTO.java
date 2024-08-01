package com.turinghealth.turing.health.utils.dto.authDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email Cannot Be Blank!")
    @Email(message = "Email must valid")
    private String email;

    @NotBlank(message = "Password Cannot Be Blank!")
    private String password;
}
