package com.turinghealth.turing.health.utils.dto.consultationDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConsultationRequestDTO {

    @NotNull(message = "Consultation Date Cannot Be Null!")
    private Date consultationDate;

    @NotNull(message = "Please Choose Your Doctor!")
    private Integer doctorId;

}
