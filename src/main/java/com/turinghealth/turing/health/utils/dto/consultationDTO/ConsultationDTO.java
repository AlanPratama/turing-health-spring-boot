package com.turinghealth.turing.health.utils.dto.consultationDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ConsultationDTO {

    private Date consultationDate;
    private String consultationUrl;
    private boolean accepted;

    private AccountDTO member;
    private AccountDTO doctor;
}
