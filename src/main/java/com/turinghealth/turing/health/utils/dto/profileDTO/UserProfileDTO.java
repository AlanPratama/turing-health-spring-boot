package com.turinghealth.turing.health.utils.dto.profileDTO;

import com.turinghealth.turing.health.entity.meta.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserProfileDTO {

    @NotBlank(message = "Name Cannot Be Blank!")
    private String name;

    @NotBlank(message = "NIK Cannot Be Blank!")
    private String nik;

    @NotBlank(message = "Phone Cannot Be Blank!")
    private String phone;

    @NotBlank(message = "Address Cannot Be Blank!")
    private String address;

    @NotBlank(message = "Email Cannot Be Blank!")
    private String email;

    @NotNull(message = "Region Cannot Be Blank!")
    private Region region;
}
