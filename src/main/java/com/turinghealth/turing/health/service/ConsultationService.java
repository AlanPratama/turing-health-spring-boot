package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationAcceptRequestDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationRequestDTO;

import java.util.List;

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


    List<ConsultationDTO> getAll();


    // ????
    // MEMBER / DOCTOR CHANGE STATUS CONSULTATION

}