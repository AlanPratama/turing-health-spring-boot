package com.turinghealth.turing.health.utils.dto.authDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
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
