package com.turinghealth.turing.health.utils.dto.profileDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {

    @NotBlank(message = "Old Password Cannot Be Blank!")
    private String oldPassword;

    @NotBlank(message = "New Password Cannot Be Blank!")
    private String newPassword;

    @NotBlank(message = "Confirm Password Cannot Be Blank!")
    private String confirmPassword;

}
