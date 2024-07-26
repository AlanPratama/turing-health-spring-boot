package com.turinghealth.turing.health.utils.dto.hospitalDTO;

import com.turinghealth.turing.health.entity.meta.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalRequestDTO {
    @NotBlank(message = "Name of hospital can't be blank")
    private String name;

    @NotBlank(message = "Name of hospital can't be blank")
    private String address;

    @NotNull(message = "Id of region can't be blank")
    private Integer regionId;

    @NotBlank(message = "Phone number can't be blank")
    private String phone;

    @NotBlank(message = "Name of province can't be blank")
    private String province;
}
