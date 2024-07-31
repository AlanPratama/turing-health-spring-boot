package com.turinghealth.turing.health.utils.dto.authDTO;

import jakarta.validation.constraints.NotBlank;
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
    private String email;
    
    @NotBlank(message = "Password Cannot Be Blank!")        
    private String password;
}
