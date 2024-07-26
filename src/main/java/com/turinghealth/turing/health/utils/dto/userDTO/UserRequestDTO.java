package com.turinghealth.turing.health.utils.dto.userDTO;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    @NotBlank(message = "Name Cannot Be Blank")
    private String name;

    @NotBlank(message = "NIK Cannot Be Blank")
    private String nik;

    @NotBlank(message = "Phone Cannot Be Blank")
    private String phone;

//    @NotBlank(message = "Name Cannot Be Blank")
    private String address;

    @NotBlank(message = "Email Cannot Be Blank")
    private String email;

    @NotBlank(message = "Password Cannot Be Blank")
    private String password;

    @NotNull(message = "Region Cannot Be Blank")
    private Integer regionId;

    @NotNull(message = "Role Cannot Be Blank")
    private Role role;
}
