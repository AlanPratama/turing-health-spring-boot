package com.turinghealth.turing.health.utils.dto.userDTO;

import com.turinghealth.turing.health.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @NotBlank(message = "Name Cannot Be Blank")
    private String name;

    @NotBlank(message = "NIK Cannot Be Blank")
    private String nik;

    @NotBlank(message = "Phone Cannot Be Blank")
    private String phone;

//    @NotBlank(message = "Address Cannot Be Blank")
    private String address;

    @NotBlank(message = "Email Cannot Be Blank")
    private String email;

    @NotNull(message = "Region Cannot Be Blank")
    private Integer regionId;

    private Role role;
}
