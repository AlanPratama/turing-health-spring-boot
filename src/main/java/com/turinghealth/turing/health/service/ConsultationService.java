package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationAcceptRequestDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationRequestDTO;

public interface ConsultationService {

    // MEMEBR START TO CONSULTATION
    // - CONSULTATION DATE (MEMBER CHOOSE THE DATE)
    // - ACCEPTED (false)
    // - MEMBER
    // - DOCTOR
    ConsultationDTO startConsultation(ConsultationRequestDTO request);

    // DOCTOR ACCEPTED CONSULTATION
    // - CONSULTATION URL
    // - ACCEPTED (true)
    ConsultationDTO acceptConsultation(ConsultationAcceptRequestDTO request, Integer id);



    // ????
    // MEMBER / DOCTOR CHANGE STATUS CONSULTATION

}
