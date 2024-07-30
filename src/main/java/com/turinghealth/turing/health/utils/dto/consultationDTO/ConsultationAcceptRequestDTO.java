package com.turinghealth.turing.health.utils.dto.consultationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultationAcceptRequestDTO {

    @NotBlank(message = "Consultation URL Cannot Be Null!")
    private String consultationUrl;

}
