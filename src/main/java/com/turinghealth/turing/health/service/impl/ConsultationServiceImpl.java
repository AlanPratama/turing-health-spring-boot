package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Consultation;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.ConsultationRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.ConsultationService;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.adviser.exception.ValidateException;
import com.turinghealth.turing.health.utils.dto.consultationDTO.AccountDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationAcceptRequestDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationRequestDTO;
import com.turinghealth.turing.health.utils.mapper.UserMapper;
import com.turinghealth.turing.health.utils.specification.ConsultationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;

    @Override
    public ConsultationDTO startConsultation(ConsultationRequestDTO request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User member = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

//        if (member.getRole() != Role.MEMBER) {
//            throw new ValidateException("Only Role Member Can Start Consultation");
//        }

        UserMiddleware.isUser(member.getRole());

        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow(() -> new NotFoundException("Doctor Not Found!"));

        if (doctor.getRole() != Role.DOCTOR) {
            throw new ValidateException("Invalid Doctor!");
        }

        Consultation consultation = Consultation.builder()
                .consultationDate(request.getConsultationDate())
                .consultationUrl(null)
                .accepted(false)
                .member(member)
                .doctor(doctor)
                .build();

        return ConsultationDTO.builder()
                .consultationDate(consultation.getConsultationDate())
                .consultationUrl(consultation.getConsultationUrl())
                .accepted(consultation.isAccepted())
                .member(UserMapper.accountDTO(member))
                .doctor(UserMapper.accountDTO(doctor))
                .build();
    }

    @Override
    public ConsultationDTO acceptConsultation(ConsultationAcceptRequestDTO request, Integer id) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User doctor = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

//        if (doctor.getRole() != Role.DOCTOR) {
//            throw new ValidateException("Only Role Doctor Can Accept Consultation");
//        }

        UserMiddleware.isDoctor(doctor.getRole());

        Consultation consultation = consultationRepository.findById(id).orElseThrow(() -> new NotFoundException("Consultation Not Found!"));

        if (!Objects.equals(consultation.getDoctor().getId(), doctor.getId())) {
            throw new NotFoundException("This Consultation Is Not Your!");
        }

        consultation.setConsultationUrl(request.getConsultationUrl());
        consultation.setAccepted(true);

        Consultation acceptedConsultation = consultationRepository.save(consultation);

        return ConsultationDTO.builder()
                .consultationDate(acceptedConsultation.getConsultationDate())
                .consultationUrl(acceptedConsultation.getConsultationUrl())
                .accepted(acceptedConsultation.isAccepted())
                .member(UserMapper.accountDTO(acceptedConsultation.getMember()))
                .doctor(UserMapper.accountDTO(acceptedConsultation.getDoctor()))
                .build();
    }

    @Override
    public List<ConsultationDTO> getAll() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        UserMiddleware.isUser(user.getRole());
        UserMiddleware.isDoctor(user.getRole());

        Specification<Consultation> spec = ConsultationSpecification.getSpecification(user);
        List<Consultation> consultations = consultationRepository.findAll(spec);

        return consultations.stream()
                .map(consultation -> ConsultationDTO.builder()
                        .consultationDate(consultation.getConsultationDate())
                        .consultationUrl(consultation.getConsultationUrl())
                        .accepted(consultation.isAccepted())
                        .member(UserMapper.accountDTO(consultation.getMember()))
                        .doctor(UserMapper.accountDTO(consultation.getDoctor()))
                        .build())
                .collect(Collectors.toList());
    }
}
