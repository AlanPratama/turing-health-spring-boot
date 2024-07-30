package com.turinghealth.turing.health.utils.dto.consultationDTO;

import com.turinghealth.turing.health.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountDTO {

    private String name;
    private String userImageLink;
    private Role role;

}
