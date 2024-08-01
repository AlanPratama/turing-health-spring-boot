package com.turinghealth.turing.health.utils.dto.authDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterDTO {
    @NotBlank(message = "Name Cannot Be Blank!")        
    private String name;
    
    @NotBlank(message = "Phone Number Cannot Be Blank!")        
    private String phone;
    
    @NotBlank(message = "Email Cannot Be Blank!")
    @Email(message = "Email must valid")
    private String email;
    
    @NotBlank(message = "Password Cannot Be Blank!")
    @Size(min = 6, message = "Password must have at least 6 character")
    private String password;
}
