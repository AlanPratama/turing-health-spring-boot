package com.turinghealth.turing.health.utils.dto.userDTO;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer id;
    private String name;
    private String nik;
    private String phone;
    private String address;
    private String email;
    private Role role;
    private Region region;
}
